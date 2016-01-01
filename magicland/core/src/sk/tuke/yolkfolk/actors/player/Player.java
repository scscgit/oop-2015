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

package sk.tuke.yolkfolk.actors.player;

import sk.tuke.gamelib2.Actor;
import sk.tuke.yolkfolk.actors.AnimatedMovement;
import sk.tuke.yolkfolk.actors.player.states.PlayerState;
import sk.tuke.yolkfolk.actors.player.states.PlayerStates;
import sk.tuke.yolkfolk.input.CustomInput;

/**
 * Rozhranie definujuce zakladne predpoklady pre posobenie actora ako hraca, ktoreho ovlada pouzivatel.
 * Protip: odporuca sa vytvorit ho ako posledny objekt v Tiled editore mapy, aby mal najvacsie ID a bol pred ostatnymi.
 * <p/>
 * Created by Steve on 2.12.2015.
 */
public interface Player extends Actor, AnimatedMovement, AirborneSupport
{
	//Constants
	//Maximum energy of a generic player
	int MAX_HP = 100;
	//If velocity becomes less than this value, Player can enter state of falling
	//hodnota -2f funguje tiez dobre
	float VELOCITY_Y_FALL = -3f;
	//If difference between old and new Y (resp. X) value is less than this value, it will be considered zero for our purposes
	float DELTA_Y_IS_ZERO = 0.01f;
	float DELTA_X_IS_ZERO = 0.01f;
	//If velocity Y (in both directions) is less than this value, Player can be considered standing on ground
	//povodna hodnota 0.1f robila problemy pri schodoch a nerovnych povrchoch
	float SPEED_Y_IS_ON_GND = 0.9f; //nova verzia bola 0.6f a smykal sa

	void setStep(float step);
	float getStep();

	void setDiamonds(int diamonds);
	int getNumberOfDiamonds();

	void setEnergy(int energy);
	int getEnergy();
	void decreaseEnergy(int energy);

	boolean addToBackpack(Actor actor);
	void showBackpack();
	void hideBackpack();

	void afterMovement();

	PlayerState defaultState();
	void setState(PlayerState state);
	PlayerState getState();
	PlayerStates changeState();

	boolean noVerticalChange();
	boolean noHorizontalChange();
	boolean standsOnSolid();

	void interpret(String commands);
	CustomInput getPlayerInput();
	void setPlayerInput(CustomInput playerInput);

	void onDeath();
	void wonTheGame();
}
