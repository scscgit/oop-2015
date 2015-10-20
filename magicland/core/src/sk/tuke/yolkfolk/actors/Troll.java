/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 *
 * scsc
 * Technicka univerzita v Kosiciach, Fakulta elektrotechniky a informatiky
 *
 * Licencia: Volny softver, Open-Source GNU GPL v3+
 * Vseobecna verejna licencia. Program je dovolene volne sirit a upravovat.
 * Upraveny program / cast programu moze ktokolvek vyuzit ako na osobne,
 * tak aj komercne ucely, ale nemoze ho vydat s vlastnym copyrightom,
 * ktory nie je kompatibilny s GNU GPL v3+.
 * gnu.org/licenses/gpl-faq.html
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see < http://www.gnu.org/licenses/ >.
 */

package sk.tuke.yolkfolk.actors;

import sk.tuke.gamelib2.Animation;
import sk.tuke.gamelib2.Message;
import sk.tuke.gamelib2.PhysicsHelper;
import sk.tuke.yolkfolk.actors.player.AbstractAnimatedActor;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * One of Player's enemies.
 * Not to be confused with your everyday internet troll.
 *
 * Created by Steve on 3.12.2015.
 */
public class Troll extends AbstractAnimatedActor
{
	//Premenne
	private boolean direction; //Smer otocenia, false=vpravo, true=vlavo

	//Konstanty
	private static final float DETECTION_RADIUS = 2.0f; //Vzdialenost v nasobkoch rozmerov Trolla, na ktoru je schopny detegovat hraca

	//Referencie na objekty
	private Message braveHeroNotice; //Iba ak je sprava null (este neexistuje), tak ju raz Troll povie
	private Player player; //Instancia hraca, ktory sa ako prvy nachadza v blizkosti Trolla

	public Troll()
	{
		super("Troll","sprites/troll.png",48,52);
		this.braveHeroNotice = null;
		this.player = null;
		this.direction = false;

		Animation animationLeft = new Animation("sprites/troll_left.png",48,52);
		setAnimationLeft(animationLeft);
		setAnimationJumpLeft(animationLeft);

		//Rychlost, ktorou bude nahanat prveho playera, ktoreho uvidi
		setStep(6);
	}

	@Override
	public void act()
	{
		//Referencia na prveho hraca, ktoreho si Troll vsimne, sa ulozi do sukromnej premennej player
		if(braveHeroNotice == null && isNear((this.player=getPlayer()),getWidth()*DETECTION_RADIUS,getHeight()*DETECTION_RADIUS))
		{
			this.braveHeroNotice = new Message("A wild troll appeared","*sniff* *sniff*,\nME SMELL A BRAVE HERO,\nme must defend this castle.", this);
			this.direction = this.player.getX()<getX();
		}

		if(this.player instanceof Player)
		{
			if(this.direction != this.player.getX()<getX())
			{
				this.direction=!this.direction;
				if(!this.direction)
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

			PhysicsHelper.setLinearVelocity(this,0,0);
			PhysicsHelper.applyForce(this,direction?-getStep():getStep(),0);
		}
	}
}
