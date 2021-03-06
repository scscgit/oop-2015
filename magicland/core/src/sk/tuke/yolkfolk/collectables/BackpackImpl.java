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

package sk.tuke.yolkfolk.collectables;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Backpack;
import sk.tuke.gamelib2.BackpackUI;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actors.AbstractActor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Intended usage:
 * <p/>
 * Do batohu sa itemy daju vkladat iba pomocou metody add().
 * Osetrenie vkadania do plneho batohu nastane automaticky notifikaciou pouzivatela a zrusenim operacie vkladania.
 * Z batohu sa itemy odstranuju iba pomocou interakcie pouzivatela v ramci metody show().
 * <p/>
 * Created by Steve on 28.11.2015.
 */
public class BackpackImpl implements Backpack
{
	//Constants
	private static final int MAX_BP_ITEMS = 3;

	//Objects
	private Actor player;
	private List<BackpackUI> listBackpackUI; //Reference to all (possibly) opened backpack instances on screen
	private Message currentMessage;

	//A collection of items carried inside the backpack
	private List<Actor> items;

	public BackpackImpl(Actor actor)
	{
		this.player = actor;
		listBackpackUI = new ArrayList<BackpackUI>();
		this.currentMessage = null;

		this.items = new ArrayList<Actor>(MAX_BP_ITEMS);
		//this.items.ensureCapacity(3);
	}

	//Add collectable actor to backpack
	@Override
	public boolean add(Actor actor)
	{
		if (actor instanceof Collectable && !this.items.contains(actor))
		{
			if (this.items.size() < MAX_BP_ITEMS)
			{
				this.items.add(actor);
				return true;
			}
			else
			{
				throw new ArrayIndexOutOfBoundsException("Backpack is full.");
			}
		}
		return false;
	}

	//Remove collectable actor from backpack by ID
	@Override
	public void remove(int i)
	{
		try
		{
			if (this.items.size() >= i + 1 && items.get(i) != null)
			{
				this.items.get(i).setPosition(this.player.getX(), this.player.getY());
				this.player.getWorld().addActor(this.items.get(i));

				this.items.remove(i);
			}
			else
			{
				throw new NoSuchElementException("You don't have any item there, cheater.");
			}
		}
		catch (NoSuchElementException exception)
		{
			if (this.player instanceof Actor)
			{
				if (this.currentMessage != null)
				{
					this.currentMessage.remove();
				}
				this.currentMessage = new Message("Inventory problem", exception.getMessage(), this.player);
			}
		}
	}

	//Show a new instance of backpack on screen
	@Override
	public void show()
	{
		BackpackUI backpackUI = new BackpackUI(player, this);
		listBackpackUI.add(backpackUI);

		boolean first = true;
		if (this.items.size() == 0)
		{
			System.out.println("Empty");
		}
		else
		{
			for (int i = 0; i < this.items.size(); i++)
			{
				AbstractActor item = (AbstractActor) this.items.get(i);
				backpackUI.setTextForButton(i + 1, item != null ? item.getName() : "Nothing");

				if (!first)
				{
					System.out.print(" / ");
				}
				else
				{
					first = false;
				}
				System.out.print(Integer.toString(i) + ". " + (item != null ? item.getName() : "Empty"));
			}
			System.out.print('\n');
		}

		backpackUI.show();
	}

	//Hide all backpacks currently shown on screen
	public void hide()
	{
		//Closes and deletes all references to old instances of backpack
		Iterator<BackpackUI> bpUIiterator = listBackpackUI.iterator();
		while (bpUIiterator.hasNext())
		{
			BackpackUI backpackUI = bpUIiterator.next();
			if (backpackUI != null)
			{
				backpackUI.close();
				bpUIiterator.remove();
			}
		}
	}

	/**
	 * Returns an iterator over a set of backpack items of type Actor.
	 *
	 * @return an Iterator.
	 */
	@Override
	public Iterator<Actor> iterator()
	{
		return items.iterator();
	}
}
