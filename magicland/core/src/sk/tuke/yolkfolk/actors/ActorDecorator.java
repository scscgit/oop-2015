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

package sk.tuke.yolkfolk.actors;

import sk.tuke.gamelib2.World;

/**
 * Kazdy dekorator nad actorom ma svoje metody, cez ktore sa musi k jeho actorovi pristupovat.
 * V pripade pridania dekoratora do sveta sa totiz jeho Actor neprida automaticky a teda to nie je PhysicalActor.
 * <p/>
 * Najefektivnejsi sposob vyzeralo byt spravenie spolocneho rozhrania, ktore bude implementovat kazdy Dekorator.
 * Toto rozhranie ale musi implementovat aj dekorovany Actor, inak nema zarucene jeho prehladne pridavanie do sveta.
 * <p/>
 * Created by Steve on 30.12.2015.
 */
public interface ActorDecorator
{
	void addToWorld(World world);
	void removeFromWorld();
}
