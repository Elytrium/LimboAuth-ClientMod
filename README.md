<img src="https://elytrium.net/src/img/elytrium.webp" alt="Elytrium" align="right">

# LimboAuth Client Mod

[![Join our Discord](https://img.shields.io/discord/775778822334709780.svg?logo=discord&label=Discord)](https://ely.su/discord)

A client mod for [LimboAuth](https://github.com/Elytrium/LimboAuth) \
Test server: [``ely.su``](https://hotmc.ru/minecraft-server-203216)

## Mod dependencies

- Architectury API

## Features of LimboAuth Client Mod

- Saves session tokens to the config file (`.minecraft/config/limboauth.yml`)
- You can set your own session token via the custom launcher

### How does session token work

1) The server makes a token - a struct that contains an issue timestamp
2) The server signs this token with a private verify key (which you can see in the LimboAuth config)
3) The server sends the token to the client, the client saves it to the config file
4) When player joins the server, servers asks client if he has a session token
5) If the player has a session token, it sends it to the server
6) The server verifies the token via the private verify key

### How to generate a session token

Pseudocode
```py
# This key must be the same in the plugin config and in the server hash issuer
verify_key = "testkey123"
issue_timestamp = unix_timestamp_millis()
player_username = "TestPlayer123"

username_bytes = utf8.string_to_bytes(lower(player_username))
timestamp_bytes = big_endian.long_to_bytes(issue_timestamp)

# siphash 2-4 (default siphash) is used here
tokenhash = siphash.hash(verify_key, byte_concat(username_bytes, timestamp_bytes))
hash_bytes = big_endian.long_to_bytes(tokenhash)

token = base64.encode_to_string(byte_concat(timestamp_bytes, hash_bytes))
```

### When does the token expire?

- The token expires if the player changes his password
- See ISSUEDTIME database field

## Donation

Your donations are really appreciated. Donations wallets/links/cards:

- MasterCard Debit Card (Tinkoff Bank): ``5536 9140 0599 1975``
- Qiwi Wallet: ``PFORG`` or [this link](https://my.qiwi.com/form/Petr-YSpyiLt9c6)
- YooMoney Wallet: ``4100 1721 8467 044`` or [this link](https://yoomoney.ru/quickpay/shop-widget?writer=seller&targets=Donation&targets-hint=&default-sum=&button-text=11&payment-type-choice=on&mobile-payment-type-choice=on&hint=&successURL=&quickpay=shop&account=410017218467044)
- Monero (XMR): 86VQyCz68ApebfFgrzRFAuYLdvd3qG8iT9RHcru9moQkJR9W2Q89Gt3ecFQcFu6wncGwGJsMS9E8Bfr9brztBNbX7Q2rfYS
