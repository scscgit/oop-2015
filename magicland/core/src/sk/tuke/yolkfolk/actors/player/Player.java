/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 *
 * scsc
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

package sk.tuke.yolkfolk.actors.player;

import sk.tuke.gamelib2.Actor;
import sk.tuke.yolkfolk.actors.player.states.PlayerState;

/**
 * Rozhranie definujuce zakladne predpoklady pre posobenie actora ako Hraca, ktoreho ovlada pouzivatel.
 *
 * Created by Steve on 2.12.2015.
 */
public interface Player extends Actor, AnimatedMovement
{
	void setStep(float step);
	float getStep();

	void setJumpHeight(float jumpHeight);
	float getJumpHeight();

	boolean isFlyable();
	void setFlyable(boolean flying);

	int getNumberOfDiamonds();
	void setDiamonds(int diamonds);

	int getEnergy();
	void setEnergy(int energy);

	boolean addToBackpack(Actor actor);
	void showBackpack();
	void hideBackpack();

	void afterMovement();

	void setState(PlayerState state);
	PlayerState getState();
}