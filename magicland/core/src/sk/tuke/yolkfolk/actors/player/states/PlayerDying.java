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

package sk.tuke.yolkfolk.actors.player.states;

import sk.tuke.gamelib2.Animation;
import sk.tuke.yolkfolk.NewWorldOrder;
import sk.tuke.yolkfolk.actions.player.Exit;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * Player in a state of permanently getting out of misery (life).
 * <p/>
 * Created by Steve on 16.12.2015.
 */
public class PlayerDying extends PlayerFrozen
{
	//Objects
	private Animation dyingAnimation;

	public PlayerDying(Player player, Animation dyingAnimation)
	{
		super(player);
		setDyingAnimation(dyingAnimation);
		stopMusic();

		//Nastavi relevantne animacie
		updateAnimation();

		//Necha hraca vykonat svoje posledne prianie pred smrtou
		getPlayer().onDeath();
	}

	private void updateAnimation()
	{
		if (getDyingAnimation() != null)
		{
			getPlayer().setAnimation(getDyingAnimation());
			getDyingAnimation().setLooping(false);
		}
	}

	//Saves a game over animation for the player
	protected final void setDyingAnimation(Animation dyingAnimation)
	{
		this.dyingAnimation = dyingAnimation;
	}

	//Returns the current instance of the dying animation
	protected final Animation getDyingAnimation()
	{
		return this.dyingAnimation;
	}

	//Po smrti zastavi hudbu
	protected final void stopMusic()
	{
		if (getPlayer().getWorld() instanceof NewWorldOrder)
		{
			NewWorldOrder newWorld = (NewWorldOrder) getPlayer().getWorld();
			newWorld.stopMusic();
		}
	}

	//Checks if the animation is currently at the end
	public boolean animationEnded()
	{
		return getDyingAnimation() != null &&
		       getDyingAnimation().getCurrentFrame() == getDyingAnimation().getFrameCount() - 1;
	}

	//Skoncenie hry povoli iba ak skoncila animacia
	@Override
	protected void addActions()
	{
		if (animationEnded())
		{
			addAction(new Exit());
		}
	}

	@Override
	public void act()
	{
		//Vykona akcie nadradenej triedy
		super.act();

		//Animacia sa bude stale hybat s hracom.
		if (animationEnded() /*&& this.gameOverMessage == null*/)
		{
			newMessage(getPlayer().getName() + " has died :(", "Game Over!");

			//Kym neskonci animacia, tak neumoznim pouzivatelovi skoncit hru.
			if (input().enterRising())
			{
				System.exit(0);
			}
		}
	}
}
