CREATE TABLE restaurants (
                             id BIGSERIAL PRIMARY KEY,
                             address VARCHAR(255) NOT NULL,
                             cuisine VARCHAR(255) NOT NULL,
                             name VARCHAR(255) NOT NULL
);

CREATE TABLE dishes (
                        id BIGSERIAL PRIMARY KEY,
                        description VARCHAR(255) NOT NULL,
                        image_url VARCHAR(255) NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        price INTEGER NOT NULL,
                        restaurant_id BIGINT NOT NULL
);

ALTER TABLE dishes
    ADD CONSTRAINT fkpslsa9mci7gsfhwukb3mx7s6n
        FOREIGN KEY (restaurant_id) REFERENCES restaurants(id);


-- Заполнение ресторанов
INSERT INTO restaurants (address, cuisine, name) VALUES
                                                     ('Sovetskaya 1', 'Fast Food', 'Dodo Pizza'),
                                                     ('Kirovskaya 6', 'Belarussian', 'Belaruski pachastunak'),
                                                     ('Mihailova 2', 'Turkish', 'Turka'),
                                                     ('Lenina 15', 'Italian', 'Mario Pasta'),
                                                     ('Pobedy 23', 'Japanese', 'Tokyo Sushi'),
                                                     ('Gorkogo 7', 'Georgian', 'Tbilisi'),
                                                     ('Nezavisimosti 45', 'American', 'Burger King'),
                                                     ('Krasnaya 12', 'Chinese', 'Dragon House'),
                                                     ('Pushkina 8', 'French', 'Le Bistro'),
                                                     ('Masherova 34', 'Mexican', 'El Mariachi');

-- Заполнение блюд для Dodo Pizza (id = 1)
INSERT INTO dishes (description, image_url, name, price, restaurant_id) VALUES
                                                                            ('Пицца с пепперони и сыром моцарелла', 'https://example.com/pepperoni.jpg', 'Пепперони', 1290, 1),
                                                                            ('Пицца с ветчиной, грибами и сыром', 'https://example.com/ham_mushroom.jpg', 'Ветчина и грибы', 1190, 1),
                                                                            ('Пицца с курицей, ананасами и сыром', 'https://example.com/hawaiian.jpg', 'Гавайская', 1350, 1),
                                                                            ('Пицца Маргарита с томатами и базиликом', 'https://example.com/margarita.jpg', 'Маргарита', 990, 1),
                                                                            ('Картофель фри с сырным соусом', 'https://example.com/fries.jpg', 'Картофель фри', 390, 1),
                                                                            ('Куриные крылышки в медово-чесночном соусе', 'https://example.com/wings.jpg', 'Куриные крылышки', 690, 1),
                                                                            ('Салат Цезарь с курицей', 'https://example.com/caesar.jpg', 'Цезарь', 890, 1),
                                                                            ('Кола, Фанта, Спрайт на выбор', 'https://example.com/soda.jpg', 'Газировка 0.5л', 190, 1);

-- Заполнение блюд для Belaruski pachastunak (id = 2)
INSERT INTO dishes (description, image_url, name, price, restaurant_id) VALUES
                                                                            ('Традиционные драники со сметаной', 'https://example.com/draniki.jpg', 'Драники', 890, 2),
                                                                            ('Суп с грибами и клецками', 'https://example.com/mushroom_soup.jpg', 'Грибной суп', 690, 2),
                                                                            ('Жаркое с говядиной и овощами', 'https://example.com/zharkoe.jpg', 'Жаркое по-белорусски', 1290, 2),
                                                                            ('Колдуны с мясной начинкой', 'https://example.com/kalduny.jpg', 'Колдуны', 990, 2),
                                                                            ('Мочанка с блинами', 'https://example.com/mochanka.jpg', 'Мочанка', 1190, 2),
                                                                            ('Домашний квас', 'https://example.com/kvas.jpg', 'Квас', 290, 2),
                                                                            ('Блины с творогом', 'https://example.com/bliny.jpg', 'Блины с творогом', 590, 2),
                                                                            ('Компот из сухофруктов', 'https://example.com/compote.jpg', 'Компот', 190, 2);

-- Заполнение блюд для Turka (id = 3)
INSERT INTO dishes (description, image_url, name, price, restaurant_id) VALUES
                                                                            ('Шаурма с курицей и овощами', 'https://example.com/shaurma.jpg', 'Шаурма', 590, 3),
                                                                            ('Кебаб из говядины на гриле', 'https://example.com/kebab.jpg', 'Кебаб', 890, 3),
                                                                            ('Турецкая пицца с мясом', 'https://example.com/lahmacun.jpg', 'Лахмаджун', 490, 3),
                                                                            ('Фалафель с хумусом', 'https://example.com/falafel.jpg', 'Фалафель', 690, 3),
                                                                            ('Чай в традиционном стакане', 'https://example.com/turkish_tea.jpg', 'Турецкий чай', 150, 3),
                                                                            ('Баклава с орехами', 'https://example.com/baklava.jpg', 'Баклава', 390, 3),
                                                                            ('Долма в виноградных листьях', 'https://example.com/dolma.jpg', 'Долма', 790, 3),
                                                                            ('Айран', 'https://example.com/ayran.jpg', 'Айран', 190, 3);

-- Заполнение блюд для Mario Pasta (id = 4)
INSERT INTO dishes (description, image_url, name, price, restaurant_id) VALUES
                                                                            ('Паста Карбонара с беконом', 'https://example.com/carbonara.jpg', 'Карбонара', 1190, 4),
                                                                            ('Лазанья с мясным соусом', 'https://example.com/lasagna.jpg', 'Лазанья', 1390, 4),
                                                                            ('Пенне с томатным соусом', 'https://example.com/penne.jpg', 'Пенне Аррабиата', 990, 4),
                                                                            ('Тирамису', 'https://example.com/tiramisu.jpg', 'Тирамису', 690, 4),
                                                                            ('Салат Капрезе', 'https://example.com/caprese.jpg', 'Капрезе', 890, 4),
                                                                            ('Брускетта с томатами', 'https://example.com/bruschetta.jpg', 'Брускетта', 490, 4),
                                                                            ('Ризотто с грибами', 'https://example.com/risotto.jpg', 'Ризотто', 1290, 4);

-- Заполнение блюд для Tokyo Sushi (id = 5)
INSERT INTO dishes (description, image_url, name, price, restaurant_id) VALUES
                                                                            ('Филадельфия с лососем', 'https://example.com/philadelphia.jpg', 'Филадельфия', 1290, 5),
                                                                            ('Калифорния с крабом', 'https://example.com/california.jpg', 'Калифорния', 1190, 5),
                                                                            ('Темпура роллы с креветкой', 'https://example.com/tempura.jpg', 'Темпура', 1390, 5),
                                                                            ('Мисо суп', 'https://example.com/miso.jpg', 'Мисо суп', 390, 5),
                                                                            ('Сашими из тунца', 'https://example.com/sashimi.jpg', 'Сашими', 1590, 5),
                                                                            ('Якисоба с курицей', 'https://example.com/yakisoba.jpg', 'Якисоба', 990, 5),
                                                                            ('Зеленый чай', 'https://example.com/green_tea.jpg', 'Зеленый чай', 190, 5);

-- Заполнение блюд для Tbilisi (id = 6)
INSERT INTO dishes (description, image_url, name, price, restaurant_id) VALUES
                                                                            ('Хачапури по-аджарски', 'https://example.com/khachapuri.jpg', 'Хачапури', 1190, 6),
                                                                            ('Хинкали с мясом', 'https://example.com/khinkali.jpg', 'Хинкали (6шт)', 890, 6),
                                                                            ('Сациви с курицей', 'https://example.com/satsivi.jpg', 'Сациви', 990, 6),
                                                                            ('Лобио с грецкими орехами', 'https://example.com/lobio.jpg', 'Лобио', 690, 6),
                                                                            ('Шашлык из свинины', 'https://example.com/shashlik.jpg', 'Шашлык', 1390, 6),
                                                                            ('Грузинское вино Киндзмараули', 'https://example.com/wine.jpg', 'Вино (бокал)', 490, 6);

-- Заполнение блюд для Burger King (id = 7)
INSERT INTO dishes (description, image_url, name, price, restaurant_id) VALUES
                                                                            ('Воппер с говяжьей котлетой', 'https://example.com/whopper.jpg', 'Воппер', 590, 7),
                                                                            ('Чизбургер', 'https://example.com/cheeseburger.jpg', 'Чизбургер', 290, 7),
                                                                            ('Картофель фри', 'https://example.com/bk_fries.jpg', 'Картофель фри', 190, 7),
                                                                            ('Наггетсы (6шт)', 'https://example.com/nuggets.jpg', 'Наггетсы', 390, 7),
                                                                            ('Молочный коктейль ванильный', 'https://example.com/shake.jpg', 'Молочный коктейль', 390, 7),
                                                                            ('Салат с курицей', 'https://example.com/bk_salad.jpg', 'Салат Цезарь', 490, 7);

-- Заполнение блюд для Dragon House (id = 8)
INSERT INTO dishes (description, image_url, name, price, restaurant_id) VALUES
                                                                            ('Курица в кисло-сладком соусе', 'https://example.com/sweet_sour.jpg', 'Кисло-сладкая курица', 890, 8),
                                                                            ('Жареный рис с овощами', 'https://example.com/fried_rice.jpg', 'Жареный рис', 690, 8),
                                                                            ('Пельмени вонтоны', 'https://example.com/wonton.jpg', 'Вонтоны', 490, 8),
                                                                            ('Утка по-пекински', 'https://example.com/peking_duck.jpg', 'Утка по-пекински', 1890, 8),
                                                                            ('Лапша с говядиной', 'https://example.com/beef_noodles.jpg', 'Лапша с говядиной', 990, 8),
                                                                            ('Зеленый чай с жасмином', 'https://example.com/jasmine_tea.jpg', 'Чай с жасмином', 190, 8);

-- Заполнение блюд для Le Bistro (id = 9)
INSERT INTO dishes (description, image_url, name, price, restaurant_id) VALUES
                                                                            ('Луковый суп с сыром', 'https://example.com/french_onion.jpg', 'Луковый суп', 790, 9),
                                                                            ('Фуа-гра с ягодным соусом', 'https://example.com/foie_gras.jpg', 'Фуа-гра', 2390, 9),
                                                                            ('Рататуй', 'https://example.com/ratatouille.jpg', 'Рататуй', 1190, 9),
                                                                            ('Круассан', 'https://example.com/croissant.jpg', 'Круассан', 290, 9),
                                                                            ('Крем-брюле', 'https://example.com/creme_brulee.jpg', 'Крем-брюле', 690, 9),
                                                                            ('Бордо вино', 'https://example.com/bordeaux.jpg', 'Бордо (бокал)', 590, 9);

-- Заполнение блюд для El Mariachi (id = 10)
INSERT INTO dishes (description, image_url, name, price, restaurant_id) VALUES
                                                                            ('Буррито с курицей', 'https://example.com/burrito.jpg', 'Буррито', 890, 10),
                                                                            ('Начос с сыром и перцем', 'https://example.com/nachos.jpg', 'Начос', 690, 10),
                                                                            ('Тако с говядиной', 'https://example.com/taco.jpg', 'Тако', 390, 10),
                                                                            ('Кесадилья с сыром', 'https://example.com/quesadilla.jpg', 'Кесадилья', 590, 10),
                                                                            ('Гуакамоле', 'https://example.com/guacamole.jpg', 'Гуакамоле', 490, 10),
                                                                            ('Маргарита коктейль', 'https://example.com/margarita_cocktail.jpg', 'Маргарита', 690, 10);