/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 * <p/>
 * Stefan Ciberaj, ZS 2015/2016
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

package sk.tuke.yolkfolk.actors.player.states;

import sk.tuke.gamelib2.Animation;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * Player is not doing anything and being bored.
 * Basically a neutral state where player can do usual stuff.
 * <p/>
 * Created by Steve on 14.12.2015.
 */
public class PlayerStanding extends AbstractGroundState
{
	//Constants
	private static final int BOREDOM_TIMER = 450;

	//Variables
	private int standbyTime;
	private Animation boredomAnimation;

	public PlayerStanding(Player player, Animation boredomAnimation)
	{
		super(player);
		this.boredomAnimation = boredomAnimation;
		resetBoredom();

		//Hrac dopadol na zem
		getPlayer().fall(0f);

		//Zastavi pohyb hraca
		stopVelocity();

		//Nastavi relevantne animacie
		updateAnimation();

		//Pred vykonanim prveho cyklu vyskusa, ci sa stav nema zmenit na novy, kedze tento stav sa spusta vzdy po dokonceni ineho pohybu
		//move();
	}

	//V pripade, ze hrac stoji sa zastavia vsetky animacie reprezentujuce pohyb
	private void updateAnimation()
	{
		getPlayer().stopAnimationLeft();
		getPlayer().stopAnimationRight();
		getPlayer().stopAnimationJump();
	}

	//Aktualizuje stav nudy hraca
	protected void updateBoredom()
	{
		if (this.standbyTime <= 0 && getPlayer() != null && this.boredomAnimation != null)
		{
			getPlayer().setAnimation(this.boredomAnimation);
		}
		else if (this.standbyTime > 0)
		{
			this.standbyTime--;
		}
	}

	//Resets the timer waiting for Player to get bored
	protected final void resetBoredom()
	{
		this.standbyTime = PlayerStanding.BOREDOM_TIMER;
	}

	//Hrac zacne vykonavat pohyb na zaklade stlacenej klavesy
	@Override
	protected void keyboardActions()
	{
		if (input().leftNotRight())
		{
			//changeState().setStateWalking(PlayerWalking.Direction.LEFT);
			interpret("set state walking left");
		}
		else if (input().rightNotLeft())
		{
			//changeState().setStateWalking(PlayerWalking.Direction.RIGHT);
			interpret("set state walking right");
		}
		else if (input().upwardNotDownward())
		{
			//changeState().setStateJumping(PlayerWalking.Direction.UP);
			interpret("set state jumping up");
		}
	}

	@Override
	public void act()
	{
		//Ak sa hrac nudi od nicnerobenia, tak sa nastavi prislusna animacia
		updateBoredom();

		//Vykona operacie, ktore je potrebne vykonat na zemi
		super.act();
	}
}
