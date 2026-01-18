# Iconuri SVG - TextOnly Web

Bibliotecă completă de iconuri SVG pentru aplicația web TextOnly, care oglindesc designul Android.

## 📁 Structură Fișiere

```
web/assets/icons/
├── icons.css              # Stiluri și clase pentru iconuri
├── icons.js               # Funcții helper și mapare iconuri
├── icons-gallery.html     # Galerie de vizualizare a iconurilor
│
├── ic-rose.svg           # Trandafir (cadou)
├── ic-heart.svg          # Inimă (cadou)
├── ic-rocket.svg         # Rachetă (cadou)
├── ic-gift-card.svg      # Card Cadou
│
├── ic-coin.svg           # Monedă (valută)
│
├── ic-mic.svg            # Microfon (comunicare)
├── ic-mic-off.svg        # Microfon Închis
├── ic-videocam.svg       # Videocam
├── ic-videocam-off.svg   # Videocam Închis
├── ic-headset.svg        # Cască
├── ic-headset-off.svg    # Cască Închisă
├── ic-attachment.svg     # Atașament
│
├── ic-add.svg            # Adaugă
├── ic-delete.svg         # Șterge
├── ic-settings.svg       # Setări
│
├── ic-group.svg          # Grup
├── ic-person-add.svg     # Adaugă Persoană
│
├── ic-star.svg           # Stea
├── ic-pin.svg            # Pivot
├── ic-crown.svg          # Coroană
└── ic-flower.svg         # Floare
```

## 🚀 Utilizare

### 1. Includere în HTML

```html
<head>
    <link rel="stylesheet" href="assets/icons/icons.css">
    <script src="assets/icons/icons.js"></script>
</head>
```

### 2. Utilizare în HTML (Injectare)

```javascript
// Metoda 1: Cu funcția getIconHTML()
document.getElementById('myElement').innerHTML = getIconHTML('rose', 'md');

// Metoda 2: Cu funcția createIcon()
const icon = createIcon('heart', 'lg', 'icon-danger');
document.body.appendChild(icon);

// Metoda 3: Direct în HTML
<img src="assets/icons/ic-rose.svg" class="icon icon-lg icon-primary" alt="Rose">
```

### 3. Clase CSS Disponibile

#### Mărimi
```html
<img src="assets/icons/ic-coin.svg" class="icon icon-sm">   <!-- 1rem -->
<img src="assets/icons/ic-coin.svg" class="icon icon-md">   <!-- 1.5rem -->
<img src="assets/icons/ic-coin.svg" class="icon icon-lg">   <!-- 2rem -->
<img src="assets/icons/ic-coin.svg" class="icon icon-xl">   <!-- 2.5rem -->
<img src="assets/icons/ic-coin.svg" class="icon icon-xxl">  <!-- 3rem -->
```

#### Culori
```html
<img src="assets/icons/ic-coin.svg" class="icon icon-primary">    <!-- Albastru -->
<img src="assets/icons/ic-coin.svg" class="icon icon-success">    <!-- Verde -->
<img src="assets/icons/ic-coin.svg" class="icon icon-danger">     <!-- Roșu -->
<img src="assets/icons/ic-coin.svg" class="icon icon-warning">    <!-- Portocaliu -->
<img src="assets/icons/ic-coin.svg" class="icon icon-info">       <!-- Cyan -->
```

#### Animații
```html
<img src="assets/icons/ic-coin.svg" class="icon icon-spin">       <!-- Rotație continuă -->
<img src="assets/icons/ic-coin.svg" class="icon icon-pulse">      <!-- Pulsație -->
```

#### Butoane cu Icoane
```html
<button class="icon-btn primary">
    <img src="assets/icons/ic-coin.svg" class="icon icon-md" alt="Coin">
</button>
```

## 📋 Referință Iconuri

### Cadouri (🎁 Gifts)
| Icon | Key | Utilizare |
|------|-----|-----------|
| 🌹 | `rose` | Trandafir (10 coins) |
| ❤️ | `heart` | Inimă (20 coins) |
| 🚀 | `rocket` | Rachetă (50 coins) |
| 🎁 | `giftCard` | Card Cadou (100 coins) |

### Valută (💰 Currency)
| Icon | Key | Utilizare |
|------|-----|-----------|
| 🪙 | `coin` | Monedă/OnlyCoins |

### Comunicare (🎤 Communication)
| Icon | Key | Utilizare |
|------|-----|-----------|
| 🎤 | `mic` | Microfon activ |
| 🔇 | `micOff` | Microfon dezactivat |
| 📹 | `videocam` | Cameră video activă |
| 📹 | `videocamOff` | Cameră video dezactivată |
| 🎧 | `headset` | Cască/Audio |
| 🔇 | `headsetOff` | Cască dezactivată |
| 📎 | `attachment` | Atașament/Fișier |

### Acțiuni (⚙️ Actions)
| Icon | Key | Utilizare |
|------|-----|-----------|
| ➕ | `add` | Adaugă/Nou |
| 🗑️ | `delete` | Șterge |
| ⚙️ | `settings` | Setări |

### Social (👥 Social)
| Icon | Key | Utilizare |
|------|-----|-----------|
| 👥 | `group` | Grup/Mulțime |
| ➕👤 | `personAdd` | Adaugă Prieten |

### Interfață (🎯 UI)
| Icon | Key | Utilizare |
|------|-----|-----------|
| ⭐ | `star` | Favorit/Rating |
| 📍 | `pin` | Locație/Pivot |
| 👑 | `crown` | Premium/VIP |
| 🌸 | `flower` | Decorație |

## 🎨 Exemple de Utilizare

### Exemplu 1: Monedă cu Animație
```html
<div class="balance-card">
    <span class="balance-amount">
        1000
        <span class="icon-spin">
            <img src="assets/icons/ic-coin.svg" class="icon icon-lg" alt="Coin">
        </span>
    </span>
</div>
```

### Exemplu 2: Grilă de Cadouri
```javascript
const gifts = [
    { name: 'Rose', key: 'rose', price: 10 },
    { name: 'Heart', key: 'heart', price: 20 },
    { name: 'Rocket', key: 'rocket', price: 50 }
];

const html = gifts.map(gift => `
    <div class="gift-card">
        ${getIconHTML(gift.key, 'lg')}
        <p>${gift.name}</p>
        <span>${gift.price} ${getIconHTML('coin', 'sm')}</span>
    </div>
`).join('');
```

### Exemplu 3: Buton cu Icon
```html
<button class="icon-btn primary">
    <img src="assets/icons/ic-person-add.svg" class="icon icon-md" alt="Add Friend">
</button>
```

## 📱 Responsive Design

Toate iconurile sunt SVG scalabile și funcționează la orice rezoluție. Clasele CSS sunt relative, deci se adaptează la dimensiunea fontului:

```css
/* La desktop (16px font size) */
.icon-md = 1.5 * 16px = 24px

/* La mobil (14px font size) */
.icon-md = 1.5 * 14px = 21px
```

## 🔄 Mapare Automată pentru Articole

```javascript
// Maparea automată a resurselor Android la icoane SVG
const ICON_MAPPING = {
    'ic_rose': 'rose',
    'ic_heart': 'heart',
    'ic_rocket': 'rocket',
    'ic_gift_card': 'giftCard',
    'ic_flower': 'flower',
    'ic_crown': 'crown',
    // ... etc
};

// Utilizare
const resourceName = 'ic_rose';
const iconHtml = getItemIcon(resourceName, 'lg');
```

## 🌐 Galerie Interactivă

Accesează galeria completă de iconuri:
```
http://localhost/icons-gallery.html
```

## 💡 Sfaturi Optimizare

1. **Refolosire**: Folosește aceleași icoane în mai multe locuri
2. **Caching**: Iconurile SVG sunt cached de browser
3. **Performance**: Dimensiunile mici de fișier (< 1KB fiecare)
4. **Culori**: Folosește `currentColor` pentru a eredita culoarea din CSS

## 🔗 Integrare cu Pagini Existente

- **profile.html** - Monedă în display balanță
- **wallet.html** - Monedă cu animație spin
- **store.html** - Iconuri cadouri și monede
- **inventory.html** - Iconuri articole
- **transactions.html** - Iconuri și monede

## 📞 Suport

Pentru a adăuga noi icoane:
1. Creați fișierul SVG în `assets/icons/`
2. Adăugați intrarea în obiectul `ICONS` din `icons.js`
3. Actualizați documentația `README.md`

---

**Ultima actualizare**: Ianuarie 2026
**Versiune**: 1.0
