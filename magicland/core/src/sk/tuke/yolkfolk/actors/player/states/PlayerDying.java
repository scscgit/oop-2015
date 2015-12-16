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
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.input.CustomInput;

/**
 * Player in a state of permanently getting out of misery (life).
 *
 * Created by Steve on 16.12.2015.
 */
public class PlayerDying extends AbstractPlayerState
{
	private Message gameOverMessage;
	private Animation dyingAnimation;

	public PlayerDying(Player player, Animation dyingAnimation)
	{
		super(player);
		this.gameOverMessage = null;
		setDyingAnimation(dyingAnimation);
	}

	protected Animation getDyingAnimation()
	{
		return this.dyingAnimation;
	}

	//Sets a game over animation for the player
	protected void setDyingAnimation(Animation dyingAnimation)
	{
		if(dyingAnimation instanceof Animation)
		{
			this.dyingAnimation = dyingAnimation;
			getPlayer().setAnimation(dyingAnimation);
			this.dyingAnimation.setLooping(false);
		}
		else
		{
			this.dyingAnimation = null;
		}
	}

	public boolean animationEnded()
	{
		return getDyingAnimation() instanceof Animation && getDyingAnimation().getCurrentFrame() == getDyingAnimation().getFrameCount()-1;
	}

	@Override
	public void act()
	{
		if(animationEnded() && gameOverMessage == null)
		{
			this.gameOverMessage = new Message("Unfortunate accident",getPlayer().getName()+" has died :(\nPress Enter to exit the game.",getPlayer());
		}

		if(CustomInput.enterRising())
		{
			System.exit(0);
		}
	}
}
