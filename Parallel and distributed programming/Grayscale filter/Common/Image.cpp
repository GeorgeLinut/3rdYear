#include "Image.h"

batman::Image::Image()
{
    m_buffer = nullptr;
}

batman::Image::Image(size_t height, size_t width)
{
    m_buffer = new uint8_t[4 * height * width];
    memset(m_buffer, 255, 4 * height * width);
    m_height = m_borderedHeight = height;
    m_width = m_borderedWidth = width;
}

void batman::Image::LoadFromFile(std::string file)
{
    uint32_t error;

    error = lodepng_decode_file(&m_buffer, &m_width, &m_height, file.c_str(), LCT_RGBA, 8);
    if (error)
    {
        throw ImageException(error);
    }

    m_borderedWidth = m_width;
    m_borderedHeight = m_height;
}

void batman::Image::StoreToFile(std::string file)
{
    LodePNGState state;
    uint8_t* png;
    uint32_t error;
    size_t size;

    lodepng_state_init(&state);

    error = lodepng_encode(&png, &size, m_buffer, m_width, m_height, &state);
    if (error)
    {
        throw ImageException(error);
    }

    error = lodepng_save_file(png, size, file.c_str());
    if (error)
    {
        throw ImageException(error);
    }

    lodepng_state_cleanup(&state);

    delete png;
}

void batman::Image::Border(size_t borderSize)
{
    uint8_t* image;

    m_borderedWidth += 2 * borderSize;
    m_borderedHeight += 2 * borderSize;
    image = new uint8_t[4 * m_borderedWidth * m_borderedHeight];
    memset(image, 255, 4 * m_borderedWidth * m_borderedHeight);

    // Copy top and bottom row on top and bottom border
    uint8_t* dest = image + 4 * (m_borderedHeight - 1) * m_borderedWidth + 4 * borderSize;
    uint8_t* source = m_buffer + 4 * (m_height - 1) * m_width;
    memcpy(dest, source, 4 * m_width);
    memcpy(image + 4 * borderSize, m_buffer, 4 * m_width);

    // Copy the lateral rows on their respective border
    for (uint32_t h = 0; h < m_height; h++)
    {
        uint8_t* destination = image + 4 * (h + borderSize) * m_borderedWidth;
        uint8_t* source = m_buffer + 4 * h * m_width;
        memcpy(destination, source, 4);
        destination += 4 * m_borderedWidth - 4;
        source += 4 * m_width - 4;
        memcpy(destination, source, 4);
    }

    // Copy the image
    for (uint32_t h = 0; h < m_height; h++)
    {
        uint8_t* destination = image + 4 * (h + borderSize) * m_borderedWidth + 4 * borderSize;
        uint8_t* source = m_buffer + 4 * h * m_width;
        memcpy(destination, source, 4 * m_width);
    }

    delete m_buffer;
    m_buffer = image;
}

void batman::Image::Unborder()
{
    uint8_t* image;
    size_t border = m_borderedHeight - m_height;
    image = new uint8_t[4 * m_width * m_height];

    for (uint32_t h = 0; h < m_height; h++)
    {
        uint8_t* dest = image + 4 * h * m_width;
        uint8_t* source = m_buffer + 4 * (h + border) * m_borderedWidth + 4 * border;
        memcpy(dest, source, 4 * m_width);
    }

    delete m_buffer;
    m_buffer = image;
}

void batman::Image::Resize(size_t height, size_t width)
{
    if (m_buffer != nullptr)
    {
        delete m_buffer;
    }

    m_buffer = new uint8_t[4 * height * width];
    memset(m_buffer, 255, 4 * height * width);
    m_height = m_borderedHeight = height;
    m_width = m_borderedWidth = width;
}

batman::RGBA batman::Image::Pixel(uint32_t h, uint32_t w)
{
    return RGBA(m_buffer + 4 * h * m_borderedWidth + 4 * w);
}

batman::Image::~Image()
{
    delete m_buffer;
}
