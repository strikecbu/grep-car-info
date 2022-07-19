import CarItem from './CarItem'

export default function Cars() {
    const posts = [
        {
            id: '62d66460ed36db6c231a8f50',
            title: '2015 BMW 4-Series Gran Coupe 420i Sport Line 自排 黑色',
            price: '93800',
            description: '里程9.6萬KM 監理參考附上',
            detail: 'https://sscars.com.tw/product/2015-bmw-4-series-gran-coupe-420i-sport-...',
            imageUrl:
                'https://sscars.com.tw/wp-content/uploads/2022/07/2022_07_10_14_36_IMG_8716-247x185.jpg',
            year: 2015,
            brand: 'bmw',
            updateTime: '2022-07-19 07:59:28',
        },
        {
            id: '62d66dsfd6db6c231a8f50',
            title: '2015 BMW 4-Series Gran Coupe 420i Sport Line 自排 黑色',
            price: '93800',
            description: '里程9.6萬KM 監理參考附上',
            detail: 'https://sscars.com.tw/product/2015-bmw-4-series-gran-coupe-420i-sport-...',
            imageUrl:
                'https://sscars.com.tw/wp-content/uploads/2022/07/2022_07_10_14_36_IMG_8716-247x185.jpg',
            year: 2015,
            brand: 'bmw',
            updateTime: '2022-07-19 07:59:28',
        },
        {
            id: '62d6646dsfsdfdsf0',
            title: '2015 BMW 4-Series Gran Coupe 420i Sport Line 自排 黑色',
            price: '93800',
            description: '里程9.6萬KM 監理參考附上',
            detail: 'https://sscars.com.tw/product/2015-bmw-4-series-gran-coupe-420i-sport-...',
            imageUrl:
                'https://sscars.com.tw/wp-content/uploads/2022/07/2022_07_10_14_36_IMG_8716-247x185.jpg',
            year: 2015,
            brand: 'bmw',
            updateTime: '2022-07-19 07:59:28',
        },
        {
            id: '62d664asdfsda6c231a8f50',
            title: '2015 BMW 4-Series Gran Coupe 420i Sport Line 自排 黑色',
            price: '93800',
            description: '里程9.6萬KM 監理參考附上',
            detail: 'https://sscars.com.tw/product/2015-bmw-4-series-gran-coupe-420i-sport-...',
            imageUrl:
                'https://sscars.com.tw/wp-content/uploads/2022/07/2022_07_10_14_36_IMG_8716-247x185.jpg',
            year: 2015,
            brand: 'bmw',
            updateTime: '2022-07-19 07:59:28',
        },
        {
            id: '62d66sdfasdfsdf31a8f50',
            title: '2015 BMW 4-Series Gran Coupe 420i Sport Line 自排 黑色',
            price: '93800',
            description: '里程9.6萬KM 監理參考附上',
            detail: 'https://sscars.com.tw/product/2015-bmw-4-series-gran-coupe-420i-sport-...',
            imageUrl:
                'https://sscars.com.tw/wp-content/uploads/2022/07/2022_07_10_14_36_IMG_8716-247x185.jpg',
            year: 2015,
            brand: 'bmw',
            updateTime: '2022-07-19 07:59:28',
        },
    ]

    return (
        <section className="mt-12 mx-auto px-4 max-w-screen-xl lg:px-8">
            <div className="text-center">
                <h1 className="text-3xl text-gray-800 font-semibold">Blog</h1>
                <p className="mt-3 text-gray-500">
                    Blogs that are loved by the community. Updated every hour.
                </p>
            </div>
            <div className="mt-12 grid gap-2 sm:grid-cols-2 lg:grid-cols-3">
                {posts.map((items, key) => (
                    <CarItem
                        key={items.id}
                        keyId={items.id}
                        img={items.imageUrl}
                        title={items.title}
                        description={items.description}
                        brand={items.brand}
                        updateTime={items.updateTime}
                        price={items.price}
                    />
                ))}
            </div>
        </section>
    )
}
