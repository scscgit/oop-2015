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

import sk.tuke.yolkfolk.actors.AbstractAnimatedDecorator;

/**
 * Abstraktne ozdobovanie abstraktneho ducha.
 * <p/>
 * Created by Steve on 30.12.2015.
 */
public abstract class AbstractGhostDecorator extends AbstractAnimatedDecorator implements Ghost
{
	//Dekorovany duch
	private Ghost ghost;

	public AbstractGhostDecorator(Ghost ghost)
	{
		//Inicializacia dekoratora
		super(ghost);
		this.ghost = ghost;
	}

	@Override
	public void initAnimation()
	{
		this.ghost.initAnimation();
	}
	@Override
	public void initMovement()
	{
		this.ghost.initMovement();
	}
	@Override
	public void becomeEnemy()
	{
		this.ghost.becomeEnemy();
	}
	@Override
	public boolean isEnemy()
	{
		return this.ghost.isEnemy();
	}
	@Override
	public void becomeAlly()
	{
		this.ghost.becomeAlly();
	}
	@Override
	public boolean isAlly()
	{
		return this.ghost.isAlly();
	}
	@Override
	public boolean exchangeKills(Ghost ghost)
	{
		return this.ghost.exchangeKills(ghost);
	}
	@Override
	public void setStep(float step)
	{
		this.ghost.setStep(step);
	}
	@Override
	public float getStep()
	{
		return this.ghost.getStep();
	}
	@Override
	public boolean isLeft()
	{
		return this.ghost.isLeft();
	}
	@Override
	public boolean isRight()
	{
		return this.ghost.isRight();
	}
	@Override
	public boolean isStopped()
	{
		return this.ghost.isStopped();
	}
	@Override
	public void runLeft()
	{
		this.ghost.runLeft();
	}
	@Override
	public void runRight()
	{
		this.ghost.runRight();
	}
	@Override
	public void stop()
	{
		this.ghost.stop();
	}
	@Override
	public void setName(String name)
	{
		this.ghost.setName(name);
	}
}
