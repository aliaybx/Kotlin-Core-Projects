package watermark

import java.awt.Color
import java.awt.Transparency
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {

    println("Input the image filename:")
    val imageName = readln()
    val imageFile = File(imageName)

    if (!imageFile.exists()) {
        println("The file $imageName doesn't exist.")
        return
    }

    val image = ImageIO.read(imageFile)

    if (image.colorModel.numColorComponents != 3) {
        println("The number of image color components isn't 3.")
        return
    }

    if (image.colorModel.pixelSize !in listOf(24, 32)) {
        println("The image isn't 24 or 32-bit.")
        return
    }

    println("Input the watermark image filename:")
    val watermarkName = readln()
    val watermarkFile = File(watermarkName)

    if (!watermarkFile.exists()) {
        println("The file $watermarkName doesn't exist.")
        return
    }

    val watermark = ImageIO.read(watermarkFile)

    if (watermark.colorModel.numColorComponents != 3) {
        println("The number of watermark color components isn't 3.")
        return
    }

    if (watermark.colorModel.pixelSize !in listOf(24, 32)) {
        println("The watermark isn't 24 or 32-bit.")
        return
    }

    if (watermark.width > image.width || watermark.height > image.height) {
        println("The watermark's dimensions are larger.")
        return
    }

    var useAlpha = false
    if (watermark.transparency == Transparency.TRANSLUCENT) {
        println("Do you want to use the watermark's Alpha channel?")
        if (readln().lowercase() == "yes") {
            useAlpha = true
        }
    }

    var useTransparentColor = false
    var transColor: Color? = null
    if (!useAlpha) {
        println("Do you want to set a transparency color?")
        if (readln().lowercase() == "yes") {
            println("Input a transparency color ([Red] [Green] [Blue]):")
            val colorInput = readln().trim().split("\\s+".toRegex())
            if (colorInput.size != 3) {
                println("The transparency color input is invalid.")
                return
            }
            val r = colorInput[0].toIntOrNull()
            val g = colorInput[1].toIntOrNull()
            val b = colorInput[2].toIntOrNull()

            if (r == null || g == null || b == null ||
                r !in 0..255 || g !in 0..255 || b !in 0..255) {
                println("The transparency color input is invalid.")
                return
            }
            transColor = Color(r, g, b)
            useTransparentColor = true
        }
    }

    println("Input the watermark transparency percentage (Integer 0-100):")
    val weightInput = readln()
    val weight = weightInput.toIntOrNull()

    if (weight == null) {
        println("The transparency percentage isn't an integer number.")
        return
    }

    if (weight !in 0..100) {
        println("The transparency percentage is out of range.")
        return
    }

    println("Choose the position method (single, grid):")
    val posMethod = readln().lowercase()

    var xPos = 0
    var yPos = 0
    var isGrid = false

    if (posMethod == "single") {
        val diffX = image.width - watermark.width
        val diffY = image.height - watermark.height
        println("Input the watermark position ([x 0-$diffX] [y 0-$diffY]):")
        val posInput = readln().trim().split("\\s+".toRegex())
        if (posInput.size != 2) {
            println("The position input is invalid.")
            return
        }
        val x = posInput[0].toIntOrNull()
        val y = posInput[1].toIntOrNull()
        if (x == null || y == null) {
            println("The position input is invalid.")
            return
        }
        if (x !in 0..diffX || y !in 0..diffY) {
            println("The position input is out of range.")
            return
        }
        xPos = x
        yPos = y
    } else if (posMethod == "grid") {
        isGrid = true
    } else {
        println("The position method input is invalid.")
        return
    }

    println("Input the output image filename (jpg or png extension):")
    val outputName = readln()
    val extension = outputName.substringAfterLast('.', "")

    if (extension.lowercase() != "jpg" && extension.lowercase() != "png") {
        println("""The output file extension isn't "jpg" or "png".""")
        return
    }

    val output = BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_RGB)

    for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            val i = Color(image.getRGB(x, y))

            val wmCoord: Pair<Int, Int>? = if (isGrid) {
                (x % watermark.width) to (y % watermark.height)
            } else if (x in xPos until (xPos + watermark.width) && y in yPos until (yPos + watermark.height)) {
                (x - xPos) to (y - yPos)
            } else {
                null
            }

            if (wmCoord == null) {
                output.setRGB(x, y, i.rgb)
                continue
            }

            val (wmX, wmY) = wmCoord
            val w = Color(watermark.getRGB(wmX, wmY), true)

            val isTransparent = (useAlpha && w.alpha == 0) ||
                    (useTransparentColor && transColor != null &&
                            w.red == transColor.red &&
                            w.green == transColor.green &&
                            w.blue == transColor.blue)

            if (isTransparent) {
                output.setRGB(x, y, i.rgb)
            } else {
                val color = Color(
                    (weight * w.red + (100 - weight) * i.red) / 100,
                    (weight * w.green + (100 - weight) * i.green) / 100,
                    (weight * w.blue + (100 - weight) * i.blue) / 100
                )
                output.setRGB(x, y, color.rgb)
            }
        }
    }

    ImageIO.write(output, extension, File(outputName))
    println("The watermarked image $outputName has been created.")
}
