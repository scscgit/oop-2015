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

package sk.tuke.yolkfolk.cinematic;

import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.GameMusic;
import sk.tuke.yolkfolk.NewWorldOrder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A good ending of the story.
 * <p/>
 * Created by Steve on 31.12.2015.
 */
public class DaisyIsFree extends AbstractCinematicStrategy
{
	//Objects
	private List<Message> messages;
	private List<String> titles;
	private List<String> texts;
	private int currentMessage;

	public DaisyIsFree()
	{
		super();
		this.messages = new ArrayList<Message>();
		this.titles = new ArrayList<String>();
		this.texts = new ArrayList<String>();
		this.currentMessage = 0;
		setDuration(900);

		//Queues all the messages
		this.titles.add("And so, our story concluded.");
		this.texts.add("Dizzy has found the final key.");
		this.titles.add("Daisy is happy");
		this.texts.add("so that they never have have to\n" +
		               "be separated from each other again.");
		this.titles.add("The Prince has lost");
		this.texts.add("and, hopefully, will never\n" +
		               "go look for them again.");
		this.titles.add("Was it fun?");
		this.texts.add("You can fund the sequel on Kickstarter!\n" +
		               "(just kiddin')");
	}

	//Rozdeli interval trvania cinematic efektu na rovnaky pocet intervalov, aky je pocet sprav
	public int part(int i)
	{
		return (getDuration() / this.texts.size()) * i;
	}

	//Posun nasledujucej spravy v osi X
	protected float addPositionX()
	{
		return -154 + this.currentMessage * 37;
	}

	//Posun nasledujucej spravy v osi Y
	protected float addPositionY()
	{
		return -112 + this.currentMessage * 57;
	}

	//Zobrazi potrebnu spravu
	protected void updateMessage()
	{
		if (getCounter() > part(this.currentMessage))
		{
			Message message = new Message(this.titles.get(this.currentMessage), this.texts.get(this.currentMessage),
			                              getZone());
			message.setPosition(getZone().getX() + addPositionX(), getZone().getY() + addPositionY());
			this.messages.add(message);
			this.currentMessage++;
		}
	}

	@Override
	public void startup()
	{
		//Runs the winning music
		if (getPlayer().getWorld() instanceof NewWorldOrder)
		{
			NewWorldOrder world = (NewWorldOrder) getPlayer().getWorld();
			world.loadMusic(GameMusic.getWinPath());
			world.finalMusic();
		}
	}

	@Override
	public void act()
	{
		if (getZone() != null)
		{
			updateMessage();
		}
	}

	@Override
	public void cleanup()
	{
		//Removes messages from the screen
		final Iterator<Message> iterator = this.messages.iterator();
		while (iterator.hasNext())
		{
			iterator.next().remove();
		}
	}
}
