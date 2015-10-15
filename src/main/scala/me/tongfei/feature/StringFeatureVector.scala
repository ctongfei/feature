package me.tongfei.feature

import scala.collection._

/**
 * A feature vector stored as a map from strings to values.
 * @author Tongfei Chen (ctongfei@gmail.com).
 * @since 0.2.0
 */
class StringFeatureVector extends DefaultMap[String, Double] {

  private val data = mutable.HashMap[String, Double]()

  def apply[A](f: Feature[A]): Double = data.getOrElse(f.name, 0.0)

  override def apply(f: String): Double = data.getOrElse(f, 0.0)

  def update[A](f: Feature[A], x: Double) = data(f.name) = apply(f) + x

  def update(f: String, x: Double) = data(f) = apply(f) + x

  def get(f: String) = data.get(f)

  def iterator = data.iterator

  def +(that: StringFeatureVector): StringFeatureVector = {
    val res = new StringFeatureVector
    for ((f, w) ← this.iterator ++ that.iterator)
      res(f) += w
    res
  }

  def -(that: StringFeatureVector): StringFeatureVector = {
    val res = new StringFeatureVector
    for ((f, w) ← this.iterator) res(f) += w
    for ((f, w) ← that.iterator) res(f) -= w
    res
  }

  def *(k: Double): StringFeatureVector = {
    val res = new StringFeatureVector
    for ((f, w) ← this)
      res(f) += w * k
    res
  }

  def dot(that: StringFeatureVector): Double = {
    var sum = 0.0
    for ((f, w) ← this)
      sum += w * that(f)
    sum
  }

  def l2Norm: Double = {
    var res = 0.0
    for ((f, w) ← this)
      res += w * w
    math.sqrt(res)
  }

  def l2Normalize = this * (1.0 / this.l2Norm)

  def l1norm: Double = {
    var res = 0.0
    for ((f, w) ← this)
      res += math.abs(w)
    res
  }

  def maxNorm = this.data.values.max

  def cosSimilarity(that: StringFeatureVector) = (this dot that) / this.l2Norm / that.l2Norm

  def <<=[A](fs: FeatureList[A]): Unit = {
    for ((f, w) ← fs.features)
      if (this.data contains f.name)
        this.data(f.name) += w
    else this.data += f.name → w
  }

  def <<=[A](f: (Feature[A], Double)): Unit = {
    this <<= FeatureList(Iterable(f))
  }

}

object StringFeatureVector {
  def apply[A](fs: FeatureList[A]) = {
    val res = new StringFeatureVector
    res <<= fs
    res
  }
}
