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

package sk.tuke.yolkfolk.actors.player;

import sk.tuke.gamelib2.Animation;

/**
 * Rozhranie definujuce prehladnu pracu s animaciami v suvislosti s pohybom hraca, pripadne ineho kompatibilneho
 * actora.
 * <p/>
 * Kazdy prikaz je inkluzivny, takze napriklad na smer doprava je potrebne pouzit stop left, run right.
 * <p/>
 * Created by Steve on 2.12.2015.
 */
public interface AnimatedMovement
{
	void setAnimationLeft(Animation aniLeft);
	void setAnimationRight(Animation aniRight);
	void setAnimationJump(Animation aniJump);
	void setAnimationJumpLeft(Animation aniJumpLeft);
	void setAnimationJumpRight(Animation aniJumpRight);

	void runAnimationLeft();
	void runAnimationRight();
	void runAnimationJump();

	void stopAnimationLeft();
	void stopAnimationRight();
	void stopAnimationJump();
}
