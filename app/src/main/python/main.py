TOKEN = '7514743838:AAHT28-9TKRIbmf9igc-9nX1IvVRhVzZzMQ'

import telebot
from telebot import types

bot = telebot.TeleBot(TOKEN)
teleNumber = ''
tgNick = ''
list = ['','']


@bot.message_handler(commands=['start'])
def phone(message):
    keyboard = types.ReplyKeyboardMarkup(row_width=1,resize_keyboard=True)
    button_phone = types.KeyboardButton("Поделиться Номером",request_contact=True)
    keyboard.add(button_phone)
    bot.send_message(message.chat.id,'Привет, поделись Данными чтобы подтвердить)',reply_markup=keyboard)

@bot.message_handler(content_types=['contact'])
def contact(message):
    if message.contact is not None:
        global teleNumber
        global tgNick
        global list
        teleNumber = message.contact.phone_number
        tgNick = message.from_user.username
        list[0] = teleNumber
        list[1] = tgNick

        bot.send_message(message.chat.id,"Спасибо ✌️ - +"+teleNumber+"\nМожешь переходить в программу.\nПроверим и дадим тебе знать")
        bot.stop_polling()


def main():
    global list,teleNumber,tgNick
    bot.polling()
    return list
