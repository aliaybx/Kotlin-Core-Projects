# Image Watermark Processor

![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blueviolet?logo=kotlin)
![Console](https://img.shields.io/badge/Platform-Console-lightgrey)
![Graphics](https://img.shields.io/badge/Domain-Image%20Processing-green)
![API](https://img.shields.io/badge/API-Java%20AWT-blue)
![Status](https://img.shields.io/badge/Status-Completed-brightgreen)

A professional-grade image watermarking tool built in Kotlin that applies **configurable watermarks** to images with support for **alpha channel transparency**, **transparency color masking**, **positioning (single/grid)**, and **custom opacity levels**. This project demonstrates practical image processing, pixel-level manipulation, and robust input validation.

---

## Overview

The Image Watermark Processor takes two images — a source image and a watermark image — and blends them together according to user-defined parameters. The user controls opacity, position (single placement or grid tiling), whether to use the watermark's alpha channel, and optional transparency color masking. The result is saved as a JPEG or PNG file. This project showcases **image I/O**, **pixel-level color manipulation**, **alpha compositing**, and **interactive CLI design**.

---

## Features

- **Image Validation** — Validates that both images are 24-bit or 32-bit RGB with exactly 3 color components
- **Dimension Check** — Ensures watermark dimensions don't exceed source image dimensions
- **Alpha Channel Support** — Optionally uses the watermark's alpha channel for transparency
- **Transparency Color Masking** — Define any RGB color to be treated as transparent in the watermark
- **Custom Opacity** — Adjustable watermark transparency percentage (0–100)
- **Flexible Positioning** — Place watermark at an exact (x, y) coordinate or tile it in a grid pattern
- **Output Format Support** — Save as JPEG or PNG via file extension detection
- **Robust Input Validation** — Comprehensive error handling for every user input step with clear error messages

---

## Technical Concepts Used

| Concept | Implementation |
|---|---|
| **BufferedImage** | Java AWT `BufferedImage` for image reading, manipulation, and writing |
| **Pixel-Level Blending** | Weighted average formula: `(opacity × wm + (100 − opacity) × img) / 100` |
| **Alpha Compositing** | Alpha channel detection via `Transparency.TRANSLUCENT` |
| **Color Masking** | RGB color comparison for transparency color detection |
| **Coordinate Mapping** | Single position (offset) and grid (modulo) coordinate systems |
| **Image I/O** | `javax.imageio.ImageIO` for reading/writing JPEG and PNG |
| **Input Validation** | Type checking (`toIntOrNull`), range validation, file existence checks |
| **`Color` API** | `java.awt.Color` for extracting RGB and alpha components |

### Blending Formula

For each pixel where the watermark is applied (and not transparent/masked):

```
blendedRed   = (opacity × wmRed   + (100 − opacity) × imgRed)   / 100
blendedGreen = (opacity × wmGreen + (100 − opacity) × imgGreen) / 100
blendedBlue  = (opacity × wmBlue  + (100 − opacity) × imgBlue)  / 100
```

---

## Challenges Solved

1. **Alpha Channel Detection and Usage** — Distinguishing between opaque images (`TYPE_INT_RGB`, `TYPE_INT_ARGB`) and those with translucency required checking `watermark.transparency == Transparency.TRANSLUCENT`. The user is prompted whether to use the alpha channel only when the watermark supports it.

2. **Transparency Color Masking** — Instead of making all pixels of a certain color fully transparent (via actual alpha), the implementation compares RGB values and simply skips blending for matching pixels, preserving the original image pixel. This avoids the complexity of modifying the image alpha channel directly.

3. **Grid Positioning** — Tiling the watermark across the entire image required a coordinate mapping using the modulo operator: `x % watermarkWidth` and `y % watermarkHeight`. This creates a seamless repeating pattern.

4. **Weighted Blending with Integer Arithmetic** — The blending formula uses only integer arithmetic (no floating point), which is efficient and avoids floating-point precision issues. Careful ordering prevents integer overflow: `(opacity * wm + (100 - opacity) * img) / 100`.

5. **Comprehensive Input Validation** — Every user input is validated at the point of entry: file existence, image format, RGB range (0–255), opacity range (0–100), position bounds, and output extension. Invalid inputs return immediately with descriptive error messages.

---

## What I Learned

- Gained practical experience with **Java AWT's `BufferedImage` API** for image manipulation
- Understood the **difference between 24-bit and 32-bit images** and how alpha channels work
- Implemented **pixel-level weighted blending** for compositing two images
- Learned about **color models and transparency types** in digital imaging
- Practiced **defensive programming** with sequential input validation
- Developed a modular **interactive CLI workflow** with branching user choices

---

## How to Run

### Prerequisites
- [Java JDK 11+](https://adoptium.net/)
- [Kotlin Compiler](https://kotlinlang.org/docs/command-line.html)

### Compile and Run

```bash
# Compile
kotlinc watermark.kt -include-runtime -d watermark.jar

# Run
java -jar watermark.jar
```

### Usage Example

```
Input the image filename:
photo.jpg
Input the watermark image filename:
logo.png
Do you want to use the watermark's Alpha channel?
yes
Do you want to set a transparency color?
no
Input the watermark transparency percentage (Integer 0-100):
50
Choose the position method (single, grid):
single
Input the watermark position ([x 0-1500] [y 0-1000]):
100 100
Input the output image filename (jpg or png extension):
output.jpg
The watermarked image output.jpg has been created.
```

---

## Future Improvements

- [ ] Add support for watermark scaling and rotation
- [ ] Implement batch processing for multiple images
- [ ] Add text-based watermarking (text overlay without an image)
- [ ] Create a GUI version with real-time preview (JavaFX or Compose Multiplatform)
- [ ] Support additional image formats (BMP, GIF, WebP)
- [ ] Add undo/redo for interactive watermark adjustments
- [ ] Implement unit tests with JUnit
- [ ] Package as a reusable library with a public API

---

## Author

**Ali Aybx** — Junior Kotlin Developer  
This project was built as part of a portfolio to demonstrate proficiency in Kotlin, image processing algorithms, and interactive CLI application development.  
[GitHub](https://github.com/aliaybx) • [LinkedIn](https://www.linkedin.com/in/ali-ayoub-233632369/)
