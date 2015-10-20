/* * * * * * * * * * * * * * * *
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package sk.tuke.oop.aliens;

import java.util.Random;

interface Matematika
{
    int add(int a, int b);
    double add(double a, double b);
    int sub(int a, int b);
    double sub(double a, double b);
}

//Menej presny kvantovy pocitac
class RandomComputer extends Computer implements Matematika, EnergyConsumer
{
    private Random randomgen;
    
    public RandomComputer()
    {
	super();
	
	//Objekty
	randomgen = new Random();
    }
    
    @Override
    public int add(int a, int b)
    {
        if(a>b)
        {
            return a+b+randomgen.nextInt(a-b);
        }
        else if(a<b)
        {
            return a+b+randomgen.nextInt(b-a);
        }
        else
        {
            return a+b+randomgen.nextInt(1);
        }
    }
    
    @Override
    public double add(double a, double b)
    {
        if(a>b)
        {
            return a+b+randomgen.nextDouble()*(a-b);
        }
        else if(a<b)
        {
            return a+b+randomgen.nextDouble()*(b-a);
        }
        else
        {
            return a+b+randomgen.nextDouble();
        }
    }
    
    @Override
    public int sub(int a, int b)
    {
        return add(a, -b);
    }
    
    @Override
    public double sub(double a, double b)
    {
        return add(a, -b);
    }
    
    public int random(int a)
    {
	return randomgen.nextInt(a);
    }

    public double random(double a)
    {
	return randomgen.nextDouble()*a;
    }

    public int random(int a, int b)
    {
	return a+randomgen.nextInt(a-b);
    }

    public double random(double a, double b)
    {
	return a+randomgen.nextDouble()*(a-b);
    }
}

/**
 * Pocitac, ktory pocita hodnoty s plavajucou desatinnou ciarkou za pouzitia OpenCL funkcii GPU.
 * 
 * @author Steve
 */
public class Computer extends AbstractAdvancedActor implements Matematika, EnergyConsumer
{
    /************************************\
    |* Week 2 - Doplnkove ulohy
    \************************************/
    
    private boolean electricity;
    
    private int animationRunning;
    private int animationStopped;
    
    public Computer()
    {
	//Inicializacie
	this.electricity=false;
	
	//Animacie
        this.animationRunning = addAnimation("computer",80,48,80);
	this.animationStopped = addAnimation("computer",80,48,80,false,false);
        updateAnimation();
    }
    
    private void updateAnimation()
    {
	if(isPowered())
	{
	    updateAnimation(this.animationRunning);
	}
	else
	{
	    updateAnimation(this.animationStopped);
	}
    }
    
    @Override
    public int add(int a, int b)
    {
	return a+b;
    }

    @Override
    public double add(double a, double b)
    {
	return a+b;
    }

    @Override
    public int sub(int a, int b)
    {
	return a-b;
    }

    @Override
    public double sub(double a, double b)
    {
	return a-b;
    }
    
    /************************************\
    |* Week 5 Switchable
    \************************************/
    
    @Override
    public void setElectricityFlow(boolean set)
    {
	this.electricity=set;
	updateAnimation();
    }
    
    public boolean isPowered()
    {
	return this.electricity;
    }
}
