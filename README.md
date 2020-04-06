[![Build Status](https://travis-ci.com/ardenit/javaclient.svg?token=Y4rJUAvZoAdBPZr95So2&branch=master)](https://travis-ci.org/ardenit/javaclient)

Клиент на Java для тестового задания на проект "Полиглотная коммуникация для научных приложений".

Клонирование репозитория:
    
    git clone https://github.com/ardenit/javaclient
    cd ./javaclient
    
Запуск клиента:

    ./gradlew run
    
Конфигурация клиента производится через файл `clientcfg.properties`

Значения байт для отправки берутся из файла `input.txt`, при его отсутствии - генерируется случайная последовательность байт.

Логи записываются в папку `logs`.
