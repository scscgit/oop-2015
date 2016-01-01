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

package sk.tuke.yolkfolk.actors.characters.ghost;

/**
 * Posle ducha hybat sa donekonecna v kladnom smere osi X.
 * <p/>
 * Created by Steve on 30.12.2015.
 */
public class MovingLeftDecorator extends AbstractGhostDecorator
{
	//Constants
	public static final float SPEED = 0.07f;

	//Dekorovany duch
	private Ghost ghost;

	public MovingLeftDecorator(Ghost ghost)
	{
		super(ghost);
		this.ghost = ghost;

		//Znovu-nastavenie pohybu
		initMovement();
	}

	@Override
	public void initMovement()
	{
		super.initMovement();

		this.ghost.setStep(MovingLeftDecorator.SPEED);
		this.ghost.runLeft();
	}
}
