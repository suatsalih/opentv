# Publishing OpenTV on Google Play

The complete playbook for the **Google Play edition**: every screen in Play Console, the answer to
every question it asks, ready-to-paste listing copy, and replies for reviewers and users. Work top
to bottom. Google Play is the only place OpenTV is distributed; GitHub carries the source code only.

---

## Contents

1. [The two editions](#1-the-two-editions)
2. [Before you start](#2-before-you-start)
3. [Get the app bundle](#3-get-the-app-bundle)
4. [Create the app](#4-create-the-app)
5. [Store listing — ready to paste](#5-store-listing--ready-to-paste)
6. [Store settings](#6-store-settings)
7. [App content — every question answered](#7-app-content--every-question-answered)
8. [The Premium product](#8-the-premium-product)
9. [Android TV](#9-android-tv)
10. [Pricing and countries](#10-pricing-and-countries)
11. [Testing, then production](#11-testing-then-production)
12. [If Google rejects the app](#12-if-google-rejects-the-app)
13. [Replies to user reviews and emails](#13-replies-to-user-reviews-and-emails)
14. [Each new version](#14-each-new-version)

---

## 1. The two editions

Only the Play edition is published. The `github` flavour stays in the source for anyone building
the app themselves; no APK of it is offered.

| | `github` flavour (self-built only) | Google Play edition |
|---|---|---|
| Built as | `.apk`, by whoever builds it | `.aab`, kept as a workflow artifact |
| Updates | Built-in updater | Google Play |
| Money | PayPal donation link | **OpenTV Premium**, a one-off in-app purchase |
| Features | Everything unlocked | Free: Live TV and the guide for one provider. Premium: the rest |
| Code | `app/src/main` + `app/src/github` | `app/src/main` + `app/src/play` |

**Premium unlocks:** a second (and further) provider, catch-up, recording, the Movies, Shows and
Recordings tabs, and extra profiles.

**What the Play edition leaves out**, because Play doesn't allow it:

| Removed | Why | What happens instead |
|---|---|---|
| Self-updater | Play apps must update through Play | Play updates it |
| PayPal donations | Payments for digital features must go through Play Billing | OpenTV Premium |
| Exact-alarm permission (`USE_EXACT_ALARM`) | Only alarm-clock and calendar apps may use it | The user can allow *Alarms & reminders* for OpenTV in Android settings. Without that, a booked recording can start a few minutes late |
| Full-screen alerts (`USE_FULL_SCREEN_INTENT`) | Only calling and alarm apps may use it | Auto-switch reminders show as a heads-up notification |
| Direct battery-exemption request | Only a short list of app types may use it | The app opens Android's battery-optimisation list, where the user can exempt OpenTV |

---

## 2. Before you start

- [ ] Your Play Console developer account (you already have one).
- [ ] **Payments profile / merchant account**: Play Console → *Settings → Payments profile*.
      Needed to sell Premium.
- [ ] **Trader status (EU Digital Services Act)**: Play Console → *Settings → Developer account →
      Account details*. Selling Premium makes you a **trader**. Google then shows your name,
      address, email and phone number to EU users. Use a business address or virtual office if you
      don't want your home address shown.
- [ ] **Issues enabled** on GitHub (*Settings → General → Features → Issues*), so the privacy
      policy and in-app "Report a bug" links work.
- [ ] A Gmail address for the test team, or a **Google Group** (easiest), for the closed test.

---

## 3. Get the app bundle

Every release builds it. Open the release's run under **Actions → Release** and download the
artifact **`opentv-play-vX.Y.Z`**. It contains the `.aab`, signed with the project's upload key.
The artifact is kept for 14 days.

---

## 4. Create the app

Play Console → **Create app**

| Question | Answer |
|---|---|
| App name | `OpenTV: IPTV Player & TV Guide` |
| Default language | English (United Kingdom) – en-GB |
| App or game | **App** |
| Free or paid | **Free** (Premium is an in-app purchase. A free app can never become paid later) |
| Declarations | Tick both: Developer Programme Policies and US export laws |

**Play App Signing:** on the first upload, accept it. Google then signs what users download, and
`upload.jks` stays your *upload* key. Keep it safe: losing it means asking Google to reset it,
which takes days.

---

## 5. Store listing — ready to paste

*Grow → Store presence → Main store listing*

### App name (30 characters max)

```
OpenTV: IPTV Player & TV Guide
```

### Short description (80 characters max)

```
A fast, remote-friendly IPTV player for TV and phone. Bring your own provider.
```

### Full description (4,000 characters max)

```
OpenTV is a beautiful, lightning-fast IPTV player built for the big screen — and just as at home on your phone and tablet.

Add the Xtream Codes login, M3U playlist or Stalker portal your provider gave you, and you're watching in seconds. No account, no sign-up, no ads, no tracking — and no server of ours in between.

━━━━━━━━━━━━━━━━━━━━
📺 MADE FOR THE LIVING ROOM
━━━━━━━━━━━━━━━━━━━━
• Designed for the remote control first: every screen works with a D-pad
• Instant channel zapping with up/down, number entry and last-channel recall
• Live preview while you browse the guide
• Runs on Android TV, Google TV, Fire TV, phones and tablets

━━━━━━━━━━━━━━━━━━━━
🗓️ A PROPER TV GUIDE
━━━━━━━━━━━━━━━━━━━━
• Full electronic programme guide (EPG) with now & next
• Programme reminders so you never miss a kick-off or a finale
• Search across channels and programmes

━━━━━━━━━━━━━━━━━━━━
⭐ YOUR CHANNELS, YOUR WAY
━━━━━━━━━━━━━━━━━━━━
• Favourites, custom categories and hidden channels
• Parental PIN for channels and categories
• Set it up from your phone: scan a QR code and type logins on your phone instead of the remote
• 30 languages

━━━━━━━━━━━━━━━━━━━━
💎 OPENTV PREMIUM — one payment, yours forever
━━━━━━━━━━━━━━━━━━━━
No subscription. Buy once and unlock:
• Several providers side by side
• Catch-up TV — rewind to programmes you missed, where your provider offers it
• Recording — one-touch, scheduled and whole-series recordings
• Movies & series with resume where you left off
• Profiles for everyone in the household

Premium follows your Google account to every device you own.

━━━━━━━━━━━━━━━━━━━━
🔒 PRIVATE BY DESIGN
━━━━━━━━━━━━━━━━━━━━
OpenTV collects nothing. Your provider details stay on your device. There are no analytics, no ads and no accounts.

━━━━━━━━━━━━━━━━━━━━
ℹ️ PLEASE NOTE
━━━━━━━━━━━━━━━━━━━━
OpenTV is a media player only. It does not include any channels, playlists or content, and is not affiliated with any provider. You need a subscription from a provider of your choice, and you are responsible for having the rights to what you watch.

Open source under GPL-3.0: github.com/suatsalih/opentv
```

### Graphics

All in `docs/play/`:

| Asset | Size | File |
|---|---|---|
| App icon | 512 × 512 PNG | `docs/play/icon-512.png` |
| Feature graphic | 1024 × 500 PNG | `docs/play/feature-graphic-1024x500.png` |
| TV banner | 1280 × 720 PNG | `docs/play/tv-banner-1280x720.png` |
| Phone screenshots | 2–8, 16:9 or 9:16, at least 1080 px | take your own (see below) |
| TV screenshots | 1–8, 1920 × 1080 | take your own (see below) |

**Screenshots:** take them with the demo playlist (`docs/review/demo.m3u`) or the test provider
(`tools/stalker_mock.py`). **Never use a real provider's channel list**: channel names, sports
crests, film posters or broadcaster logos in screenshots are the most common reason IPTV players
are rejected. On a TV, take them with `adb exec-out screencap -p > shot.png`.

---

## 6. Store settings

*Grow → Store presence → Store settings*

| Field | Answer |
|---|---|
| App category | **Video Players & Editors** |
| Tags | Video players, Streaming, TV, Media player (pick what Play offers) |
| Email | `suat.salih@gmail.com` |
| Website | `https://suatsalih.github.io/opentv/` |
| Phone | Leave blank (optional) |
| External marketing | Your choice |

---

## 7. App content — every question answered

*Policy and programmes → App content*. Each card below matches one in Play Console.

### Privacy policy

```
https://github.com/suatsalih/opentv/blob/main/docs/PRIVACY.md
```

### Ads

> Does your app contain ads? → **No, my app does not contain ads**

### App access

> **All or some functionality in my app is restricted** → Add instructions

Reviewers have no IPTV provider, so give them the free demo playlist of public test streams:

| Field | Value |
|---|---|
| Name | Demo playlist |
| Username / password | Leave blank. Tick "No other information is required" if offered |
| Instructions | *(paste below)* |

```
OpenTV is a player: content comes from the user's own provider. To review it, use our demo
playlist of free public test streams (Big Buck Bunny, Apple BipBop, Sintel):

1. Open the app and choose "M3U playlist URL" on the "Add your provider" screen.
2. Name: Demo
3. Playlist URL: https://raw.githubusercontent.com/suatsalih/opentv/main/docs/review/demo.m3u
4. Save. The channels appear under "Test streams" — select one to play.

Premium features (several providers, catch-up, recording, movies & series, profiles) are unlocked
by the in-app product "OpenTV Premium" (premium_unlock) via Google Play Billing. The free edition
shows a Premium dialog when a locked feature is selected.
```

### Content rating

Start the questionnaire. Email: `suat.salih@gmail.com`. Category: **All other app types**.

| Question | Answer |
|---|---|
| Violence, blood, gore | No |
| Fear / horror | No |
| Sexuality, nudity | No |
| Offensive language | No |
| Controlled substances (drugs, alcohol, tobacco) | No |
| Crude humour | No |
| Gambling, simulated gambling | No |
| Users can interact or exchange content | No |
| Shares the user's location | No |
| Allows purchase of digital goods | **Yes** (OpenTV Premium) |
| Unrestricted internet access | **Yes**. It plays streams from any address the user enters. Being honest here avoids a re-rating later |
| Is it a web browser or search engine? | No |

Expect a low rating with the notes *In-app purchases* and *Unrestricted internet*.

### Target audience and content

| Question | Answer |
|---|---|
| Target age groups | **18 and over** only |
| Could the app unintentionally appeal to children? | **No** |
| Store presence for children | Not applicable |

Choosing 18+ keeps the app outside the stricter Families policy.

### News apps

> Is your app a news app? → **No**

### Data safety

| Question | Answer |
|---|---|
| Does your app collect or share any of the required user data types? | **No** |
| Is all data encrypted in transit? | Not asked when nothing is collected |
| Account creation | **My app does not allow users to create an account** |
| Data deletion URL | Not needed (no account) |

Why "No" is correct: Google counts data as *collected* only when it leaves the device for the
developer or a third party. OpenTV sends provider logins only to the provider the user typed in,
has no analytics or crash reporting, and its phone set-up page runs on the local network. Google
Play processes purchases under Google's own policy.

The summary users see: *"No data collected · No data shared with third parties."*

### Government apps

> **No**

### Financial features

> **My app doesn't provide any financial features**

### Health

> **My app doesn't have any health features**

### Advertising ID

> Does your app use advertising ID? → **No**

### Foreground service permissions

Play asks this because the app uses `FOREGROUND_SERVICE_MEDIA_PLAYBACK`.

| Field | Answer |
|---|---|
| Type | **Media playback** |
| Tasks | Tick **Media playback** and, if listed, **Digital video recording (DVR) on Android TV** |
| Description | *(paste below)* |
| Video | Link to a 30-second unlisted YouTube video (see below) |

```
OpenTV is a live TV player. The media playback foreground service keeps live TV playing when the
user leaves the player (picture-in-picture, audio in the background), with a media notification
to pause or stop. A second media playback service keeps a user-started or scheduled TV recording
(DVR) running when the screen turns off or the user leaves the app, showing an ongoing
"Recording" notification with a Stop button. Both stop as soon as playback or the recording ends.
```

**The video**, recorded on a phone with the screen recorder and the demo playlist:

1. Play a channel, press Home: the picture-in-picture window and the notification are visible.
2. Start a recording, go Home: the "Recording" notification is visible. Tap Stop.

### Other declarations you may see

| Card | Answer |
|---|---|
| Exact alarms (`USE_EXACT_ALARM`) | Not shown. The Play edition doesn't use it |
| Full-screen intent | Not shown. The Play edition doesn't use it |
| Photo and video permissions | Not shown. The app requests no media permissions |
| COVID-19 apps | My app is not a publicly available COVID-19 contact-tracing or status app |

---

## 8. The Premium product

*Monetise with Play → Products → One-time products → Create product*

| Field | Value |
|---|---|
| Product ID | `premium_unlock`. **It must match exactly, because it is in the code. It cannot be changed or reused later** |
| Name | `OpenTV Premium` |
| Description | `Unlock several providers, catch-up, recording, movies & series and profiles. One payment, yours forever.` |
| Price | **£9.99**. Play converts it for other countries; adjust any you like |
| Tax category | Digital app sales (the default) |

Then click **Activate**. Until it is active, the app shows "Get Premium" without a price, and
buying says Google Play isn't available.

**What you receive:** Google adds VAT and takes a 15 % service fee on your first $1M of yearly
earnings. From £9.99 in the UK that leaves you about **£7.05**. Payouts arrive monthly.

---

## 9. Android TV

*Test and release → Advanced settings → Form factors → + Add form factor → Android TV*

1. Opt in, then upload the TV banner and TV screenshots in the store listing's **Android TV**
   section.
2. Google runs an extra **TV quality review** (usually 1–3 weeks). It checks:
   - the app opens from the TV home screen (it does: leanback launcher plus banner);
   - everything works with a D-pad, with no touch needed (it does);
   - no portrait-only screens (none).
3. If it fails, you get an email with screenshots of the problem. Your phone release isn't affected.

Fire TV isn't Google Play. It needs the Amazon Appstore, which is separate (ask if you want it).

---

## 10. Pricing and countries

*Test and release → Production → Countries / regions*: **add all**.

Remove a country only if Google's review email asks you to. The app is free, so there's nothing to
set on the pricing page itself.

---

## 11. Testing, then production

New personal developer accounts must run a **closed test with at least 12 testers for 14 days in
a row** before Google allows a production release. Check the current rule on the Play Console
dashboard.

1. **Test and release → Closed testing → Create track** (name it "Beta").
2. Upload the `.aab`. Release name: `0.12.0`. Release notes:
   ```
   <en-GB>
   First Google Play release of OpenTV. Live TV and the full guide are free;
   OpenTV Premium unlocks several providers, catch-up, recording, movies & series and profiles.
   </en-GB>
   ```
3. Testers: add a Google Group or a list of emails, then share the **opt-in link**.
4. **Test Premium without paying:** *Settings → License testing*. Add the testers' Gmail
   addresses. Their purchases use test cards and are never charged.
5. Ask testers to open the app at least every few days. Google checks that testers keep using it.
6. After 14 days: **Dashboard → Apply for production**. The questions and answers:

| Question | Suggested answer |
|---|---|
| How did you recruit testers? | Friends, family and members of the open-source project's community, invited by email |
| How easy was it to recruit? | Neither easy nor difficult |
| How did testers use the app? | Added their own providers and the demo playlist, watched live TV, used the guide, reminders and favourites, and tested Premium purchase and restore with license-test accounts, on phones and Android TV |
| Summary of feedback | Testers checked zapping speed, guide loading on large playlists, remote navigation, and buying and restoring Premium. Fixes were shipped during the test |
| Who is the intended audience? | Adults who already have an IPTV subscription and want a fast, private, remote-friendly player on Android TV and phones |
| How does the app provide value? | A free, ad-free, privacy-respecting player with a proper TV guide and a one-off Premium instead of a subscription |
| Expected installs in year one | 1,000–10,000 |
| What changes did you make based on testing? | List any fixes from the test (see `CHANGELOG.md`) |
| Is the app ready for production? | Yes |

7. Once approved: **Production → Create new release → Add from library** (the same bundle) →
   roll out to 100 %.

---

## 12. If Google rejects the app

Reply from *Policy status → the issue → Appeal*. Each answer below matches the rejection you'd
get.

### "Intellectual property" or "Unauthorised use of copyrighted content"

The most common rejection for IPTV players. First check that **no screenshot, the icon or the
description** shows a channel, team, film or broadcaster. Then appeal:

```
OpenTV is a general-purpose media player, like VLC or MX Player. It contains no channels,
playlists, links or content, and it does not direct users to any source of content. Users add a
playlist URL or login from a provider they choose. The store listing states this, the screenshots
show only public test streams (Big Buck Bunny, Sintel, Apple BipBop), and the description says
users are responsible for having the rights to what they watch. The app is open source
(github.com/suatsalih/opentv), so its behaviour can be verified. We ask you to reinstate it.
```

### "App access — we could not access your app"

The reviewer couldn't find content. Check that the demo playlist URL opens in a browser, then
resubmit the **App access** instructions from section 7. Reply:

```
The app requires the user's own IPTV provider. Our App access instructions provide a free demo
playlist of public test streams: in the app choose "M3U playlist URL" on the "Add your provider" screen and enter
https://raw.githubusercontent.com/suatsalih/opentv/main/docs/review/demo.m3u
```

### "Metadata" (description, title or graphics)

Remove anything Google quotes. Usual causes: words like "free" or "best" in the title, too many
emojis, or keyword lists. The copy in section 5 avoids these. If they object to the emojis, swap
them for plain `•` headings.

### "Payments policy"

Make sure you uploaded the **Play** bundle (`opentv-play-…`), not a self-built `github` APK: only
the Play edition hides the PayPal link. Reply:

```
All digital features are sold only through Google Play Billing (product premium_unlock). The app
contains no other payment method or link to one.
```

### "Foreground service" declaration rejected

Re-record the video so the notification is clearly visible in both cases, and point out that
Google's own definition of media playback covers *DVR functionality on Android TV*.

### "Broken functionality" or crashes

Check *Quality → Android vitals → Crashes and ANRs* for the stack trace, and open a GitHub issue
with it.

---

## 13. Replies to user reviews and emails

Copy, adjust and reply from *Quality → Ratings and reviews*. Keep replies short and friendly; you
can edit them later.

**"There are no channels" / "It doesn't work"**
> Thanks for trying OpenTV! It's a player, so it doesn't come with channels — you add the
> playlist or login your IPTV provider gave you ("Add your provider" on the first screen). If your
> provider's details don't load, email us at suat.salih@gmail.com and we'll help.

**"Where can I get a playlist / subscription?"**
> OpenTV is a player only, so we can't recommend or provide content. Your provider supplies the
> playlist or login; OpenTV plays it.

**"Why pay for Premium?"**
> Live TV and the guide are free forever with no ads. Premium is a one-off payment, not a
> subscription, for the extras that cost the most to build: several providers, catch-up,
> recording, movies & series and profiles. It also keeps the project going — thank you!

**"I bought Premium but it's locked" (new phone, reinstall)**
> Sorry about that! Open Settings → Premium → **Restore purchase** while signed in to the same
> Google account you bought it with. If it still doesn't unlock, email suat.salih@gmail.com with
> your Google Play order number (GPA.…) and we'll sort it out.

**"I want a refund"**
> Of course. Within 48 hours you can request one yourself at play.google.com → your profile →
> Payments & subscriptions → Budget & history. After that, email us your order number and we'll
> refund it from our side.
*(Refund it under Play Console → Order management. Premium locks itself again automatically.)*

**"Purchase pending"**
> Your payment method (e.g. cash or family approval) hasn't completed yet. Premium unlocks by
> itself the moment Google confirms it.

**"Recording started late" / "Reminder didn't switch channel"**
> On newer Android versions, open Android Settings → Apps → OpenTV → Alarms & reminders and allow
> it, and in OpenTV Settings → Recording allow it to ignore battery optimisation. That lets
> bookings start on the dot.

**"Buffering" / "Freezes"**
> Buffering almost always comes from the provider's server or the connection. Try another
> channel, a wired connection, or your provider's other server address. If only OpenTV buffers
> and other players don't, email us the details.

**"Is my data safe?" / "Do you see my login?"**
> No. OpenTV has no servers and collects nothing: your login stays on your device and goes only to
> your provider. The code is open for anyone to check: github.com/suatsalih/opentv

**"Is this legal?"**
> OpenTV is a player, like VLC. It's legal to use with content you have the rights to watch, for
> example a subscription from a licensed provider.

**"Will it come to Fire TV / Samsung / LG?"**
> OpenTV is on Google Play only for now, so it isn't available for Fire TV, Samsung or LG TVs.

---

## 14. Each new version

1. Release on GitHub as usual (see `SETUP.md`). That builds both editions.
2. Download `opentv-play-vX.Y.Z` from the Release workflow run.
3. Play Console → your track → **Create new release** → upload the `.aab`.
4. Release notes: paste that version's section from `CHANGELOG.md`, wrapped in
   `<en-GB> … </en-GB>`. Keep it under 500 characters.
5. Roll out. For production, a **staged rollout** (start at 20 %) lets you halt a bad release.
