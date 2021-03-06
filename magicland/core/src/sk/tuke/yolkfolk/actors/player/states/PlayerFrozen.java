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

import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actions.player.Exit;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * Player frozen in space and time. This state is used for cutscenes and special purposes.
 * <p/>
 * Created by Steve on 28.12.2015.
 */
public class PlayerFrozen extends AbstractPlayerState
{
	//Objects
	private Message freezeMessage;

	public PlayerFrozen(Player player)
	{
		super(player);
		this.freezeMessage = null;
		updateAnimation();
	}

	protected void removeMessage()
	{
		if (this.freezeMessage != null)
		{
			this.freezeMessage.remove();
		}
	}

	protected Message newMessage(String title, String message)
	{
		removeMessage();
		return this.freezeMessage = new Message(title, message, getPlayer());
	}

	private void updateAnimation()
	{
		//Vypne animacie
		getPlayer().stopAnimationJump();
		getPlayer().stopAnimationLeft();
		getPlayer().stopAnimationRight();
	}

	//Prida relevantne akcie
	protected void addActions()
	{
		addAction(new Exit());
	}

	@Override
	public void act()
	{
		//Zastavi pohyb do stran, hrac moze iba padat
		stopVelocity(true);

		//Zmrazeny hrac moze hru iba skoncit
		resetActions();
		addActions();
		runActions(getPlayer());
	}
}
