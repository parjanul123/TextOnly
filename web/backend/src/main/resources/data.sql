-- Sample store items for TextOnly

-- Frames
INSERT INTO store_items (name, type, price, resource_name) VALUES
('Frame Ploaie', 'FRAME', 50, 'ic_frame_rain'),
('Frame Foc', 'FRAME', 100, 'frame_fire');

-- Emoticons
INSERT INTO store_items (name, type, price, resource_name) VALUES
('Emoji Fericit', 'EMOTICON', 10, 'emote_happy'),
('Emoji Pisică', 'EMOTICON', 20, 'emote_cat'),
('Emoji Trist', 'EMOTICON', 15, 'emote_sad');

-- Gifts (these are in code, not database)
-- Gifts are defined in StoreService.getAvailableGifts()
