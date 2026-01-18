// Icon paths mapping for easy usage
const ICONS = {
    // Gifts
    rose: '/assets/icons/ic-rose.svg',
    heart: '/assets/icons/ic-heart.svg',
    rocket: '/assets/icons/ic-rocket.svg',
    giftCard: '/assets/icons/ic-gift-card.svg',
    
    // Currency
    coin: '/assets/icons/ic-coin.svg',
    
    // Communication
    mic: '/assets/icons/ic-mic.svg',
    micOff: '/assets/icons/ic-mic-off.svg',
    videocam: '/assets/icons/ic-videocam.svg',
    videocamOff: '/assets/icons/ic-videocam-off.svg',
    headset: '/assets/icons/ic-headset.svg',
    headsetOff: '/assets/icons/ic-headset-off.svg',
    attachment: '/assets/icons/ic-attachment.svg',
    
    // Actions
    add: '/assets/icons/ic-add.svg',
    delete: '/assets/icons/ic-delete.svg',
    settings: '/assets/icons/ic-settings.svg',
    
    // Social
    group: '/assets/icons/ic-group.svg',
    personAdd: '/assets/icons/ic-person-add.svg',
    
    // UI
    star: '/assets/icons/ic-star.svg',
    pin: '/assets/icons/ic-pin.svg',
    crown: '/assets/icons/ic-crown.svg',
    flower: '/assets/icons/ic-flower.svg'
};

// Helper function to create an icon element
function createIcon(iconKey, size = 'md', className = '') {
    const iconPath = ICONS[iconKey];
    if (!iconPath) {
        console.warn(`Icon ${iconKey} not found`);
        return null;
    }
    
    const img = document.createElement('img');
    img.src = iconPath;
    img.alt = iconKey;
    img.className = `icon icon-${size} ${className}`;
    return img;
}

// Helper to insert icon as HTML
function getIconHTML(iconKey, size = 'md', className = '') {
    const iconPath = ICONS[iconKey];
    if (!iconPath) {
        console.warn(`Icon ${iconKey} not found`);
        return '';
    }
    return `<img src="${iconPath}" alt="${iconKey}" class="icon icon-${size} ${className}">`;
}

// Icon mapping for store items (resource names to icon keys)
const ICON_MAPPING = {
    'ic_rose': 'rose',
    'ic_heart': 'heart',
    'ic_rocket': 'rocket',
    'ic_gift_card': 'giftCard',
    'ic_flower': 'flower',
    'ic_crown': 'crown',
    'ic_coin': 'coin',
    'frame_neon': 'star',
    'frame_rain': 'flower',
    'frame_glitch': 'star',
    'emote_happy': 'smile',
    'emote_sad': 'frown',
    'emote_love': 'heart',
    'emote_laugh': 'smile'
};

// Get icon for store items
function getItemIcon(resourceName, size = 'lg') {
    const iconKey = ICON_MAPPING[resourceName] || 'star';
    return getIconHTML(iconKey, size, 'item-icon');
}
