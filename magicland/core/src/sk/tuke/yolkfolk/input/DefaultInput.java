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

package sk.tuke.yolkfolk.input;

import sk.tuke.gamelib2.Input;

/**
 * Static default states, that PMD didn't let me have in CustomInput class.
 * <p/>
 * Created by Steve on 28.12.2015.
 */
public class DefaultInput
{
	public static boolean leftDefault()
	{
		return Input.isKeyPressed(Input.Key.LEFT) || Input.isKeyPressed(Input.Key.A);
	}

	public static boolean rightDefault()
	{
		return Input.isKeyPressed(Input.Key.RIGHT) || Input.isKeyPressed(Input.Key.D);
	}

	public static boolean upwardDefault()
	{
		return Input.isKeyPressed(Input.Key.UP) || Input.isKeyPressed(Input.Key.W) ||
		       Input.isKeyPressed(Input.Key.SPACE);
	}

	public static boolean downwardDefault()
	{
		return Input.isKeyPressed(Input.Key.DOWN) || Input.isKeyPressed(Input.Key.S);
	}

	public static boolean enterRisingDefault()
	{
		return Input.isKeyJustPressed(Input.Key.ENTER) || Input.isKeyJustPressed(Input.Key.E);
	}

	public static boolean escapeDefault()
	{
		return Input.isKeyPressed(Input.Key.ESCAPE);
	}
}
