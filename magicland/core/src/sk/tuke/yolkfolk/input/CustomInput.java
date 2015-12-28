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

package sk.tuke.yolkfolk.input;

/**
 * Extendable support of custom input override rules of keyboard and/or mouse events.
 * <p/>
 * Created by Steve on 15.12.2015.
 */
public class CustomInput
{
	//Movement keys
	public boolean left()
	{
		return DefaultInput.leftDefault();
	}

	public boolean leftNotRight()
	{
		return left() && !right();
	}

	public boolean right()
	{
		return DefaultInput.rightDefault();
	}

	public boolean rightNotLeft()
	{
		return right() && !left();
	}

	@Deprecated
	public boolean exclusiveLeftOrRight()
	{
		boolean left = left();
		boolean right = right();
		return (left || right) && !(left && right);
	}

	public boolean upward()
	{
		return DefaultInput.upwardDefault();
	}

	public boolean upwardNotDownward()
	{
		return upward() && !downward();
	}

	public boolean downward()
	{
		return DefaultInput.downwardDefault();
	}

	public boolean downwardNotUpward()
	{
		return downward() && !upward();
	}

	//Utility keys
	public boolean enterRising()
	{
		return DefaultInput.enterRisingDefault();
	}

	public boolean escape()
	{
		return DefaultInput.escapeDefault();
	}
}
