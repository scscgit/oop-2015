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

package sk.tuke.yolkfolk.actors.player.states;

import sk.tuke.gamelib2.Animation;
import sk.tuke.gamelib2.PhysicsHelper;
import sk.tuke.yolkfolk.input.CustomInput;
import sk.tuke.yolkfolk.actions.*;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * Player is not doing anything and being bored.
 * Basically a neutral state where player can do usual stuff.
 *
 * Created by Steve on 14.12.2015.
 */
public class PlayerStanding extends AbstractPlayerState
{
	private static final int BOREDOM_TIMER = 450;

	private int standbyTime;
	private Animation boredomAnimation;

	public PlayerStanding(Player player, Animation boredomAnimation)
	{
		super(player);
		resetBoredom();
		this.boredomAnimation = boredomAnimation;

		//Zastavi pohyb hraca
		stopVelocity();

		//Nastavi relevantne animacie
		updateAnimation();

		//Pred vykonanim prveho cyklu vyskusa, ci sa stav nema zmenit na novy, kedze tento stav sa spusta vzdy po dokonceni ineho pohybu
		move();
	}

	//V pripade, ze hrac stoji sa zastavia vsetky animacie reprezentujuce pohyb
	protected void updateAnimation()
	{
		getPlayer().stopAnimationLeft();
		getPlayer().stopAnimationRight();
		getPlayer().stopAnimationJump();
	}

	//Aktualizuje stav nudy hraca
	protected void updateBoredom()
	{
		if(this.standbyTime<=0 && getPlayer() instanceof Player && this.boredomAnimation instanceof Animation)
		{
			getPlayer().setAnimation(this.boredomAnimation);
		}
		else if(this.standbyTime>0)
		{
			this.standbyTime--;
		}
	}

	//Resets the timer before Dizzy gets bored
	protected void resetBoredom()
	{
		this.standbyTime = BOREDOM_TIMER;
	}

	//Zacne vykonavat pohyb na zaklade stlacenej klavesy
	protected void move()
	{
		if(CustomInput.up())
		{
			setStateJumping(PlayerWalking.Direction.UP);
		}

		if(CustomInput.exclusiveLeftOrRight())
		{
			if(CustomInput.left())
			{
				setStateWalking(PlayerWalking.Direction.LEFT);
			}
			else if(CustomInput.right())
			{
				setStateWalking(PlayerWalking.Direction.RIGHT);
			}
		}
	}

	@Override
	public void act()
	{
		updateBoredom();

		//Hrac dopadol na zem
		getPlayer().fall();

		//Vykonanie akcii
		resetActions();
		addAction(new Use());
		addAction(new Exit());
		addAction(new Cheats());
		runActions();

		//Vyskusa, ci je potrebne zmenit stav
		move();

		//Ak hrac zomrel, tak nastavim prislusny stav
		if(isPlayerDead())
		{
			setStateDying();
		}
	}
}
