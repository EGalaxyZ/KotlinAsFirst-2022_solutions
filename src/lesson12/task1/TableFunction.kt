@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

import java.util.TreeMap

/**
 * Класс "табличная функция".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 16.
 * Объект класса хранит таблицу значений функции (y) от одного аргумента (x).
 * В таблицу можно добавлять и удалять пары (x, y),
 * найти в ней ближайшую пару (x, y) по заданному x,
 * найти (интерполяцией или экстраполяцией) значение y по заданному x.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class TableFunction {
    val list = TreeMap<Double, Double>()

    /**
     * Количество пар в таблице
     */
    val size: Int get() = list.size

    /**
     * Добавить новую пару.
     * Вернуть true, если пары с заданным x ещё нет,
     * или false, если она уже есть (в этом случае перезаписать значение y)
     */
    fun add(x: Double, y: Double): Boolean = list.put(x, y) == null

    /**
     * Удалить пару с заданным значением x.
     * Вернуть true, если пара была удалена.
     */
    fun remove(x: Double): Boolean = list.remove(x) != null

    /**
     * Вернуть коллекцию из всех пар в таблице
     */
    fun getPairs(): Collection<Pair<Double, Double>> = list.toList()

    /**
     * Вернуть пару, ближайшую к заданному x.
     * Если существует две ближайшие пары, вернуть пару с меньшим значением x.
     * Если таблица пуста, бросить IllegalStateException.
     */
    fun findPair(x: Double): Pair<Double, Double>? = when {
        list[x] != null -> Pair(x, list[x]!!)
        list.isNotEmpty() -> Pair(list.lowerKey(x), list[list.lowerKey(x)]!!)
        else -> throw IllegalStateException()
    }

    /**
     * Вернуть значение y по заданному x.
     * Если в таблице есть пара с заданным x, взять значение y из неё.
     * Если в таблице есть всего одна пара, взять значение y из неё.
     * Если таблица пуста, бросить IllegalStateException.
     * Если существуют две пары, такие, что x1 < x < x2, использовать интерполяцию.
     * Если их нет, но существуют две пары, такие, что x1 < x2 < x или x > x2 > x1, использовать экстраполяцию.
     */
    fun getValue(x: Double): Double {
        if (list.size == 1) return list.values.first()
        if (list.isEmpty()) throw IllegalStateException()
        if (list[x] != null) return list[x]!!
        val hx = list.higherKey(x)
        val lx = list.lowerKey(x)
        if (hx == null && lx != null) {
            val x2 = list.lowerKey(lx)
            return list[x2]!! + (list[lx]!! - list[x2]!!) / (lx - x2) * (x - x2)
        }
        if (lx == null && hx != null) {
            val x2 = list.higherKey(hx)
            return list[hx]!! + (list[x2]!! - list[hx]!!) / (x2 - hx) * (x - hx)
        } else return list[lx]!! + (list[hx]!! - list[lx]!!) * (x - lx) / (hx - lx)
    }

    /**
     * Таблицы равны, если в них одинаковое количество пар,
     * и любая пара из второй таблицы входит также и в первую
     */
    override fun equals(other: Any?): Boolean = (other is TableFunction) && (other.list == this.list)
}