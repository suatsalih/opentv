# Publishing OpenTV on Google Play

Everything needed to put the **Google Play edition** of OpenTV on the Play Store, in the order you
do it. The sideloaded GitHub edition is unaffected by any of this.

## The two editions

| | GitHub edition | Google Play edition |
|---|---|---|
| Built as | `.apk`, attached to each GitHub release | `.aab`, kept as a workflow artifact |
| Updates | Built-in updater | Google Play |
| Money | PayPal donation link | **OpenTV Premium**, one-off in-app purchase |
| Features | Everything unlocked | Free: Live TV + guide for one provider. Premium: the rest |
| Code | `app/src/main` + `app/src/github` | `app/src/main` + `app/src/play` |

**Premium unlocks:** a second (and further) provider, catch-up, recording, the Movies, Shows and
Recordings tabs, and extra profiles.

## 1. Get the app bundle

Every release builds it. Open the release's workflow run under **Actions → Release** and download
the artifact **`opentv-play-vX.Y.Z`** — it contains the signed `.aab`. It is signed with the
project's upload key (the same key as the GitHub APK).

## 2. Create the app in Play Console

1. **Create app** → name **OpenTV**, default language English (UK), **App**, **Free**.
2. Accept the declarations (developer programme policies, US export laws).

## 3. App signing

On the first upload, Play offers **Play App Signing** — accept it. Play then signs what users
download; your `upload.jks` stays the *upload* key. Keep `upload.jks` safe as before: losing it
means asking Google to reset the upload key, which takes days.

## 4. The Premium product

**Monetize → Products → In-app products → Create product**

| Field | Value |
|---|---|
| Product ID | `premium_unlock` (must match exactly — it is in the code) |
| Name | OpenTV Premium |
| Description | Unlock several providers, catch-up, recording, movies & series and profiles. One payment, no subscription. |
| Price | £9.99 (Play converts it for other countries; adjust any you like) |

Then **Activate** it. Before this the app shows "Get Premium" without a price, and buying says
Google Play isn't available.

Purchases need a **merchant account** (Play Console → Settings → Payments profile) — set it up
if Play asks.

## 5. Android TV

**Release → Advanced settings → Form factors → Add form factor → Android TV**, then opt in. TV apps
get an extra review for remote-control use. The TV listing needs:

- a **TV banner**, 1280 × 720 PNG (the app already has an in-app banner; export it at that size)
- at least one **TV screenshot**, 1920 × 1080

## 6. Store listing

**Short description** (80 characters max)

> IPTV player for Android TV and phones. Bring your own provider. No account.

**Full description**

> OpenTV is a fast, remote-friendly IPTV player for Android TV, Google TV, Fire TV, phones and
> tablets. Add the Xtream Codes login or M3U playlist your provider gave you and watch — no
> account, no sign-up, no server of ours in between.
>
> FREE
> • Live TV with a full programme guide (EPG) and live preview
> • Favourites, categories, search and channel hiding
> • Fast zapping, parental PIN, 31 languages
>
> OPENTV PREMIUM — one payment, no subscription
> • Several providers
> • Catch-up TV where your provider offers it
> • Recording, scheduled and series recordings
> • Movies and series with resume
> • Profiles for the household
>
> OpenTV is a player only. It contains no channels, playlists or content, and is not affiliated
> with any provider. You need a subscription from a provider of your choice.
>
> Open source (GPL-3.0): github.com/suatsalih/opentv

Keep the listing — text, screenshots, icon — free of channel names, logos, sports, films or
anything that looks like content being offered. IPTV players are reviewed with that in mind.

**Screenshots:** take them with the test provider (`tools/stalker_mock.py`) or a plain playlist,
never a real provider's channel list.

## 7. App content (policy forms)

| Form | Answer |
|---|---|
| Privacy policy | `https://github.com/suatsalih/opentv/blob/main/docs/PRIVACY.md` |
| Ads | No ads |
| App access | All features available without special access (a provider login is the user's own) |
| Content rating | Utility/streaming player; no user-generated content shared by the app; answer "no" to violence etc. — the app shows only what the user's own provider supplies |
| Target audience | 18+ (IPTV players are not designed for children) |
| Data safety | **No data collected, no data shared.** Provider details stay on the device; purchases are handled by Google Play |
| Government / financial / health | No |

## 8. Testing track, then production

New personal developer accounts must run a **closed test** with at least 12 testers for 14 days
before production access (check the current rule in Play Console → Dashboard).

1. **Testing → Closed testing → Create track**, upload the `.aab`, add testers by email (a Google
   Group is easiest).
2. Share the opt-in link. Testers can buy Premium with Play's **license testing** accounts
   (Settings → License testing) without being charged.
3. After the 14 days, **Apply for production**, then promote the release.

## Each new version

1. Release on GitHub as usual (see `SETUP.md`) — that builds both editions.
2. Download `opentv-play-vX.Y.Z` from the Release workflow run.
3. Play Console → your track → **Create new release** → upload the `.aab` → release notes (copy
   the CHANGELOG section) → roll out.
