/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 *
 * Stefan Ciberaj, ZS 2015/2016
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
import sk.tuke.yolkfolk.CustomInput;
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
		this.standbyTime = BOREDOM_TIMER;
		this.boredomAnimation = boredomAnimation;

		//Zastavi pohyb hraca
		stop();

		//Nastavi relevantne animacie
		updateAnimation();
	}

	//V pripade, ze hrac stoji sa zastavia vsetky animacie reprezentujuce pohyb
	protected void updateAnimation()
	{
		getPlayer().stopAnimationLeft();
		getPlayer().stopAnimationRight();
		getPlayer().stopAnimationJump();
	}

	//Aktualizuje stav nudy hraca.
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
				setStateWaking(PlayerWalking.Direction.LEFT);
			}
			else if(CustomInput.right())
			{
				setStateWaking(PlayerWalking.Direction.RIGHT);
			}
		}
	}

	//Zastavi pohyb hraca
	protected void stop()
	{
		PhysicsHelper.setLinearVelocity(getPlayer(), 0, PhysicsHelper.getLinearVelocity(getPlayer())[1]);
	}

	protected void setStateJumping(PlayerWalking.Direction direction)
	{
		getPlayer().setState(new PlayerJumping(getPlayer(),direction));
	}

	protected void setStateWaking(PlayerWalking.Direction direction)
	{
		getPlayer().setState(new PlayerWalking(getPlayer(),direction));
	}

	@Override
	public void act()
	{
		updateBoredom();

		//Vykonanie akcii
		resetActions();
		addAction(new Use());
		addAction(new Exit());
		addAction(new Cheats());
		runActions();

		move();
	}
}
