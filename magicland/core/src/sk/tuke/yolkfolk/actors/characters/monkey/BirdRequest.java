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

import sk.tuke.yolkfolk.actors.State;

/**
 * Stav, v ktorom bude hrac oboznameny s pravidlami a vtakmi.
 * <p/>
 * Created by Steve on 28.12.2015.
 */
public class BirdRequest extends AbstractMonkeyState
{
	public BirdRequest(Monkey monkey)
	{
		super(monkey);
		setCounter(0);

		//Prva sprava sa zobrazi pri hracovi
		newMessage("Hello there, stranger!",
		           "I can see on your face that you'd like\na shiny key that opens castle doors.",
		           getDizzy());
	}

	public void setCounter(int counter)
	{
		getMonkey().setCounter(counter);
	}
	public int getCounter()
	{
		return getMonkey().getCounter();
	}

	@Override
	public State setNextState()
	{
		State state = new WaitingForBirds(getMonkey());
		getMonkey().setState(state);
		return state;
	}

	@Override
	public void act()
	{
		//Increase counter
		setCounter(getCounter() + 1);

		//After enough time passes, if Dizzy comes near (or still is), tells him to collect birds
		if (getMonkey().getCounter() > Monkey.STATE_0_COUNTER && isNear(getDizzy()))
		{
			newMessage("You are lucky today!",
			           "If you help me collect " + Monkey.BIRDS_REQUIRED +
			           " birds,\nI can give you my key!", getMonkey());
			setNextState();
		}
	}
}
