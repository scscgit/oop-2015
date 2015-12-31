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

package sk.tuke.yolkfolk.actors.characters.ghost;

import sk.tuke.gamelib2.Animation;

/**
 * A bad ghost.
 * GhostImpl is already White implicitly.
 * <p/>
 * Created by Steve on 28.12.2015.
 */
public class WhiteGhostDecorator extends AbstractGhostDecorator
{
	//Constants
	public static final String NAME = "WhiteGhost";

	//Decorated ghost
	Ghost ghost;

	public WhiteGhostDecorator(Ghost ghost)
	{
		//Inicializacia dekoratora
		super(ghost);
		this.ghost = ghost;

		//Nahradenie animacii ducha novou farbou
		Animation leftAnimation = new Animation("sprites/ghost_left.png", 48, 42);
		leftAnimation.setPingPong(true);
		Animation rightAnimation = new Animation("sprites/ghost_right.png", 48, 42);
		rightAnimation.setPingPong(true);
		this.ghost.setAnimationNormal(rightAnimation);
		this.ghost.setAnimationLeft(leftAnimation);
		this.ghost.setAnimationJumpLeft(leftAnimation);
		this.ghost.setAnimationRight(rightAnimation);
		this.ghost.setAnimationJumpRight(rightAnimation);

		//Nastavenie hodnot bieleho ducha
		this.ghost.setName(WhiteGhostDecorator.NAME);
	}

	/*@Override
	public void act()
	{
		super.act();

		//Prvy act sa nevykona
		if (!this.initialized)
		{
			this.initialized = true;
			return;
		}
	}
	*/
}
