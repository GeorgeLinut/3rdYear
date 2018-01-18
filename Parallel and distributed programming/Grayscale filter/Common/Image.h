#ifndef IMAGE_H_
#define IMAGE_H_

#include <exception>

#include "lodepng.hpp"

namespace batman
{
    class RGBA
    {
    public:
        RGBA() { } 

        uint8_t& Red() { return m_pixel[0]; }
        uint8_t& Green() { return m_pixel[1]; }
        uint8_t& Blue() { return m_pixel[2]; }
        uint8_t& Opacity() { return m_pixel[3]; }

        ~RGBA() { }

    private:
        RGBA(uint8_t* pixel) : m_pixel(pixel) { }

        friend class Image;
        uint8_t* m_pixel;
    };

    class Image
    {
    public:
        Image();
        Image(size_t height, size_t width);
        
        void LoadFromFile(std::string file);
        void StoreToFile(std::string file);

        void Border(size_t borderSize);
        void Unborder();

        void Resize(size_t height, size_t width);

        size_t Size() { return m_height * m_width; }
        size_t Width() { return m_width; }
        size_t Height() { return m_height; }
        size_t BorderedWidth() { return m_borderedWidth; }
        size_t BorderedHeight() { return m_borderedHeight; }

        size_t BufferSize() 
        { 
            return 4 * m_borderedWidth * m_borderedHeight * sizeof(uint8_t); 
        }

        uint8_t* Buffer() { return m_buffer; }

        RGBA Pixel(uint32_t h, uint32_t w);
        
        ~Image();

    private:
        uint8_t* m_buffer;
        uint32_t m_width;
        uint32_t m_height;
        uint32_t m_borderedWidth;
        uint32_t m_borderedHeight;
    };

    class ImageException : public std::runtime_error
    {
    public:
        ImageException(uint32_t lodepngError) : runtime_error("") 
        {
            sprintf(message, "ERROR: %u: %s\n", lodepngError, lodepng_error_text(lodepngError));
        }

        const char* what() const throw()
        {
            return message;
        }

    private:
        char message[1000];
        uint32_t error;
    };
}

#endif // !IMAGE_H_
