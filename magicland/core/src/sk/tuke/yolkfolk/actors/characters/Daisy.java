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

import sk.tuke.gamelib2.Item;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.actors.Greeter;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * A generic Greeter example. Can be possibly saved by our Hero.
 * <p/>
 * Daisy is not an item, but needs to intersect.
 * <p/>
 * Created by Steve on 10.11.2015.
 */
public class Daisy extends AbstractActor implements Greeter, Item
{
	//Constants
	public static final String name = "Daisy";
	//Celkovy pocet pozdravov, ktore budu unikatne
	public static final int numberOfGreetings = 3;
	//Vzdialenost v nasobkoch velkosti Greetera, na ktoru ked sa hrac vzdiali, dostane za odmenu dalsi pozdrav
	public static final int RADIUS_OF_FORGETTING = 14;

	//Variables
	//Informacia o tom, ci hrac dostal uz pozdrav, alebo ma dostat dalsi
	private boolean greetingDone;
	//Aktualny pozdrav
	private int currentGreeting;

	//Objects
	//Aktualna sprava zobrazena hracovi
	private Message currentMessage;
	//Instancia prveho hraca, ktory bol pozdraveny. Aby to nebolo trapne, tak sa inym zdravit nebude.
	private Player firstPlayer;

	public Daisy()
	{
		super(Daisy.name, "sprites/daisy.png", 25, 25);
		this.greetingDone = false;
		this.currentGreeting = 0;
		this.currentMessage = null;
		this.firstPlayer = null;
	}

	public boolean greetPlayer(Player player)
	{
		//First met player will be remembered forever
		if (this.firstPlayer == null)
		{
			this.firstPlayer = player;
		}

		//If the player requires greeting, he shall receive one
		if (this.firstPlayer == player && !this.greetingDone)
		{
			nextGreeting(player);
			this.greetingDone = true;
			return true;
		}
		else
		{
			return false;
		}
	}

	protected boolean haveMessage()
	{
		return this.currentGreeting < numberOfGreetings;
	}

	//Shows a new message and deletes a possible previous one
	protected Message newMessage(String headline, String text)
	{
		if (this.currentMessage != null)
		{
			this.currentMessage.remove();
		}
		this.currentMessage = new Message(headline, text, this);
		return this.currentMessage;
	}

	protected void nextGreeting(Player player)
	{
		if (haveMessage())
		{
			switch (this.currentGreeting)
			{
				case 0:
					newMessage("Greetings", "Hi " + player.getName() +
					                        "!\nI would really appreciate, if a hero\nlike you could save me from here.");
					break;
				case 1:
					newMessage("Hello",
					           "Welcome back, " + player.getName() + "!\nBut really, can you save me now?\nPleeease!");
					break;
				case 2:
					newMessage(player.getName() + "!",
					           "Why are you back so soon?\nI still can't see myself being saved by you :(");
					break;
			}
			this.currentGreeting++;
		}
		else
		{
			newMessage("Come on :(", "If you were some REAL hero,\nI would've been saved thrice already.");
		}
	}

	@Override
	public void act()
	{
		//If the player already received greeting, but has gone too far, he deserves another one
		if (this.greetingDone && this.firstPlayer != null &&
		    !isNear(this.firstPlayer, getWidth() * Daisy.RADIUS_OF_FORGETTING,
		            getHeight() * Daisy.RADIUS_OF_FORGETTING))
		{
			this.greetingDone = false;
			this.currentMessage.remove();
		}
	}
}
