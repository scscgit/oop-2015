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

package sk.tuke.yolkfolk.actors.characters.monkey;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actors.State;
import sk.tuke.yolkfolk.spaces.MonkeyStoneSpace;

/**
 * Stav, v ktorom opica caka na vtaky.
 * <p/>
 * Created by Steve on 28.12.2015.
 */
public class WaitingForBirds extends AbstractMonkeyState
{
	public WaitingForBirds(Monkey monkey)
	{
		super(monkey);

		//Inicializacia priestoru na kamene
		getMonkey().setStoneSpace(null);
	}

	@Override
	public State setNextState()
	{
		State state = new WaitingForStones(getMonkey());
		getMonkey().setState(state);
		return state;
	}

	//Skoci na stav za nasledujucim stavom
	public State skipNextState()
	{
		State nextState = setNextState();
		if (nextState instanceof MonkeyState)
		{
			return ((MonkeyState) nextState).setNextState();
		}
		return nextState;
	}

	//Zabezpeci existenciu priestoru pre zbieranie kamenov, pripadne vrati false v pripade chyby
	public boolean findStoneSpace()
	{
		if (getMonkey().getStoneSpace() != null)
		{
			return true;
		}

		for (Actor actor : getMonkey().getWorld())
		{
			if (actor instanceof MonkeyStoneSpace)
			{
				getMonkey().setStoneSpace((MonkeyStoneSpace) actor);
				return true;
			}
		}
		return false;
	}

	@Override
	public void act()
	{
		//Ked hrac nazbieral dost vtakov, tak dostane dalsiu ulohy a nastavi sa prislusny stav.
		if (getDizzy() != null && getDizzy().getCaughtBirds() >= Monkey.BIRDS_REQUIRED && isNear(getDizzy()))
		{
			//Tentokrat si opica vypyta kamene
			newMessage("Whoa!",
			           "So many birds, I can feed my family now.\n"      +
			           "I am sorry, but before I give you my precious\n" +
			           "key, can you bring me "                          +
			           Monkey.STONES_REQUIRED                            +
			           " stones?\n"                                      +
			           "'tis the last thing, I promise.", getMonkey());

			//Inicializacia priestoru pre hadzanie kamenov a nastavenie dalsieho stavu
			if (!findStoneSpace())
			{
				//Map error handling by free gift to the player, he shall forgive
				new Message("Oww...",
				            "System error number, I don't know, 555.\nI am terribly sorry, but\nthe map is broken. But don't worry.\nI'll let ya pass for now.",
				            getDizzy());
				skipNextState();
			}
			else
			{
				setNextState();
			}
		}
	}
}
