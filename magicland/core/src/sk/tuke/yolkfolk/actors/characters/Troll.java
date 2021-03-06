/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 * <p/>
 * scsc
 * Technicka univerzita v Kosiciach, Fakulta elektrotechniky a informatiky
 * <p/>
 * Licencia: Volny softver, Open-Source GNU GPL v3+
 * Vseobecna verejna licencia. Program je dovolene volne sirit a upravovat.
 * Upraveny program / cast programu moze ktokolvek vyuzit ako na osobne,
 * tak aj komercne ucely, ale nemoze ho vydat s vlastnym copyrightom,
 * ktory nie je kompatibilny s GNU GPL v3+.
 * gnu.org/licenses/gpl-faq.html
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see < http://www.gnu.org/licenses/ >.
 */

package sk.tuke.yolkfolk.actors.characters;

import sk.tuke.gamelib2.Animation;
import sk.tuke.gamelib2.Message;
import sk.tuke.gamelib2.PhysicsHelper;
import sk.tuke.yolkfolk.GameMusic;
import sk.tuke.yolkfolk.actors.AbstractAnimatedActor;
import sk.tuke.yolkfolk.actors.Swimmable;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * One of Player's enemies.
 * Not to be confused with your everyday internet troll.
 * <p/>
 * Created by Steve on 3.12.2015.
 */
public class Troll extends AbstractAnimatedActor implements Swimmable
{
	//Constants
	public static final String NAME = "Troll";
	//Vzdialenost v nasobkoch rozmerov Trolla, na ktoru je schopny detegovat hraca
	private static final float DETECTION_RADIUS = 1.2f;
	//Maximalna rychlost, ktorou moze Troll padat (vo vode)
	private static final float MAX_FALL_VELOCITY = 0.8f;
	//Doba, po ktorej sa Troll utopi aj keby ho nezabil hrac
	private static final int DROWN_TIME = 250;

	//Variables
	//Smer otocenia, false=vpravo, true=vlavo
	private boolean direction;
	private boolean swimming;
	private int drownCounter;

	//Referencie na objekty
	//Iba ak je sprava null (este neexistuje), tak ju raz Troll povie
	private Message braveHeroNotice;
	//Instancia hraca, ktory sa ako prvy nachadza v blizkosti Trolla
	private Player player;

	public Troll()
	{
		super(Troll.NAME, "sprites/troll.png", 48, 52);
		this.braveHeroNotice = null;
		this.player = null;
		this.direction = false;
		this.swimming = false;
		this.drownCounter = 0;

		Animation animationLeft = new Animation("sprites/troll_left.png", 48, 52);
		setAnimationLeft(animationLeft);
		setAnimationJumpLeft(animationLeft);

		//Rychlost, ktorou bude nahanat prveho playera, ktoreho uvidi
		setStep(6);
	}

	protected void updateAnimation()
	{
		if (!this.direction)
		{
			stopAnimationLeft();
			runAnimationRight();
		}
		else
		{
			stopAnimationRight();
			runAnimationLeft();
		}
	}

	//Akcie, ktore nastanu po smrti Trolla po zabiti hracom
	protected void onDeath(boolean makeSound)
	{
		if (makeSound)
		{
			GameMusic.playFinishHim();
		}
		if (this.braveHeroNotice != null)
		{
			this.braveHeroNotice.remove();
		}
	}

	protected void noticeFirstPlayer()
	{
		if (this.braveHeroNotice == null)
		{
			this.player = getPlayer();
			if (this.player != null &&
			    isNear(this.player, getWidth() * DETECTION_RADIUS, getHeight() * DETECTION_RADIUS))
			{
				this.braveHeroNotice = new Message("A wild troll appeared",
				                                   "*sniff* *sniff*,\nME SMELL A BRAVE HERO,\nme must defend this castle.",
				                                   this);
				this.direction = this.player.getX() < getX();
				updateAnimation();
			}
		}
	}

	//Ak sa utopi, tak zomrie
	private boolean dieIfDrowns()
	{
		if (intersectsAbove(this.player, this.player.getHeight() / 2))
		{
			onDeath(true);
			removeFromWorld();
			return true;
		}
		else if (this.drownCounter > Troll.DROWN_TIME)
		{
			//Silently dies
			onDeath(false);
			removeFromWorld();
			return true;
		}
		else
		{
			this.drownCounter++;
		}
		return false;
	}

	//Akcie vykonavane pravidelne kym ma nahanat hraca. Vracia true ak nastala zmena vo svete
	private boolean followingPlayer()
	{
		//Ak su na zemi, tak Troll je silnejsi a vyhra suboj, teda zrani svojho nepriatela
		if (!this.swimming && isNear(this.player, this.player.getWidth() / 2, this.player.getHeight() / 2))
		{
			this.player.decreaseEnergy(1);
		}
		//Ak ho jeho uhlavny nepriatel najde vo vode, tak ho udusi (skocenim na hlavu), a teda zabije
		else if (this.swimming && dieIfDrowns())
		{
			return true;
		}

		//Otoci sa smerom k hracovi
		if (this.direction != this.player.getX() < getX())
		{
			this.direction = !this.direction;
			updateAnimation();
		}

		//Kym zije, bude sa pohybovat smerom k hracovi
		float yVelocity = PhysicsHelper.getLinearVelocity(this)[1];
		PhysicsHelper.setLinearVelocity(this, 0, (yVelocity < Troll.MAX_FALL_VELOCITY && this.swimming)
			? -Troll.MAX_FALL_VELOCITY : yVelocity);
		PhysicsHelper.applyForce(this, direction ? -getStep() : getStep(), 0);
		return false;
	}

	@Override
	public void act()
	{
		//Referencia na prveho hraca, ktoreho si Troll vsimne, sa ulozi do sukromnej premennej player
		noticeFirstPlayer();

		//Ak si uz vsimol hraca, tak ho bude nahanat az do smrti
		if (this.braveHeroNotice != null && this.player != null)
		{
			followingPlayer();
		}
	}

	//Troll sa zacne topit
	@Override
	public void swim()
	{
		this.swimming = true;
	}

	//Troll sa prestane topit (teoreticky by nemalo nastat, zalezi na mape)
	@Override
	public void noswim()
	{
		this.swimming = false;
	}
}
