# Privacy

OpenTV collects nothing. There is no analytics SDK, no crash reporter, no telemetry, and no
network call to any domain the project controls — because the project controls none.

## What is stored, and where

Everything lives in a SQLite database in the app's private storage on your device:

- Your provider's address, username and password
- The channel, movie and series catalogue downloaded from your provider
- The guide (EPG)
- Your favourites, hidden channels and resume positions

None of it is uploaded anywhere. There is no account to create and no server to sync with.

## Purchases (Google Play edition)

Buying OpenTV Premium is handled entirely by Google Play: OpenTV never sees your payment details.
The app asks Google Play whether this Google account owns Premium and keeps a single yes/no on
the device so it can open unlocked while offline. Google's own privacy policy covers the purchase.

## Who your device talks to

Only your provider, at the address you entered — and, in the Google Play edition, Google Play
to check or make the Premium purchase. Channel logos and posters are fetched
from whatever URLs your provider puts in its catalogue, which are usually its own servers but
may be third parties — that is your provider's choice, not ours.

## Cleartext HTTP

The app allows plain HTTP (`usesCleartextTraffic="true"`). The overwhelming majority of IPTV
panels are HTTP-only; refusing them would make the app useless to most of the people it is for.

Be aware of what that means: **on an HTTP connection, your provider username and password are
sent unencrypted**, and anyone able to observe the network between you and your provider can
read them. This is a property of how these services are built, not of OpenTV. If your provider
offers an HTTPS endpoint, use it.

## Uninstalling

Removing the app removes the database and everything in it.

## Contact

Questions or privacy concerns: **suat.salih@gmail.com**, or open an issue at
https://github.com/suatsalih/opentv/issues for anything that can be public.
