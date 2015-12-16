/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 *
 * Stefan Ciberaj, ZS 2015/2016
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

package sk.tuke.yolkfolk.input;

import sk.tuke.gamelib2.Input;

/**
 * Extendable support of custom input override rules of keyboard and/or mouse events.
 *
 * Created by Steve on 15.12.2015.
 */
public class CustomInput
{
	protected static final boolean leftDefault()
	{
		return Input.isKeyPressed(Input.Key.LEFT) || Input.isKeyPressed(Input.Key.A);
	}

	protected static final boolean rightDefault()
	{
		return Input.isKeyPressed(Input.Key.RIGHT) || Input.isKeyPressed(Input.Key.D);
	}

	protected static final boolean upDefault()
	{
		return Input.isKeyPressed(Input.Key.UP) || Input.isKeyPressed(Input.Key.W) || Input.isKeyPressed(Input.Key.SPACE);
	}

	protected static final boolean downDefault()
	{
		return Input.isKeyPressed(Input.Key.DOWN) || Input.isKeyPressed(Input.Key.S);
	}

	protected static boolean enterRisingDefault()
	{
		return Input.isKeyJustPressed(Input.Key.ENTER) || Input.isKeyJustPressed(Input.Key.E);
	}

	protected static boolean escapeDefault()
	{
		return Input.isKeyPressed(Input.Key.ESCAPE);
	}

	public static boolean left()
	{
		return CustomInput.leftDefault();
	}

	public static boolean right()
	{
		return CustomInput.rightDefault();
	}

	public static boolean exclusiveLeftOrRight()
	{
		boolean left = left();
		boolean right = right();
		return (left || right) && !(left && right);
	}

	public static boolean up()
	{
		return CustomInput.upDefault();
	}

	public static boolean down()
	{
		return CustomInput.downDefault();
	}

	public static boolean enterRising()
	{
		return CustomInput.enterRisingDefault();
	}

	public static boolean escape()
	{
		return CustomInput.escapeDefault();
	}
}
