// Функция для задержки, чтобы избежать ограничения частоты запросов
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

// Функция для получения данных по REST API с добавленным User-Agent
async function fetchAdverts(url) {
    try {
        const response = await fetch(url, {
            method: 'GET',
            headers: {

                'Content-Type': 'application/json',
                'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3' // Добавляем User-Agent
            }
        });

        // Проверка успешности запроса
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        // Парсинг ответа в формате JSON
        const data = await response.json();
        return data.lastSoldAdverts; // Извлекаем массив lastSoldAdverts

    } catch (error) {
        console.error('Ошибка при запросе:', error);

        // Обработка ошибки 423 (заблокировано)
        if (error.message.includes('423')) {
            console.error('Ресурс временно заблокирован. Попробуйте позже.');
        }
    }
}

// Функция для расчета минимальной, средней и максимальной цены
// Функция для расчета минимальной, средней и максимальной цены
function calculatePriceStats(ads, currency) {
    const SIX_MONTHS_AGO = new Date();
    SIX_MONTHS_AGO.setMonth(SIX_MONTHS_AGO.getMonth() - 6);

    // Отфильтровать объявления за последние полгода
    const recentAds = ads.filter(ad => new Date(ad.publishedAt) >= SIX_MONTHS_AGO);

    // Получить все цены в выбранной валюте
    const prices = recentAds.map(ad => ad.price[currency]?.amount || 0);

    // Если нет данных для анализа
    if (prices.length === 0) {
        return { min: 0, avg: 0, max: 0, count: 0 };
    }

    const minPrice = Math.min(...prices);
    const maxPrice = Math.max(...prices);
    const avgPrice = prices.reduce((sum, price) => sum + price, 0) / prices.length;

    return { min: minPrice, avg: avgPrice, max: maxPrice, count: prices.length };
}

// const apiUrl = 'https://api.av.by/offer-types/cars/price-statistics?brand=8&model=5863&generation=4885&year=2021&engine_type=1&drive_type=4&engine_capacity=2000&available_year=2021';
const  apiUrl = 'https://api.av.by/offer-types/cars/price-statistics?brand=8&model=2496&generation=10073&year=2021&engine_type=1&drive_type=2&engine_capacity=2000&available_year=2021';

async function main() {
    // Задержка между запросами, если требуется
    await sleep(1000); // Пауза в 1 секунду

    // Получение объявлений по API
    const adverts = await fetchAdverts(apiUrl);

    // Если объявления получены, вычисляем статистику по ценам
    if (adverts) {
        const stats = calculatePriceStats(adverts, 'usd'); // Рассчитываем статистику для USD
        console.log('Статистика цен (USD):', stats);
    }
}

// Запуск основного процесса
main();
