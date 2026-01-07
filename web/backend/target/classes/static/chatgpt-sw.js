// chatgpt-sw.js
self.addEventListener('fetch', event => {
  const url = new URL(event.request.url);
  if (url.pathname === '/chatgpt-proxy') {
    event.respondWith(
      fetch('https://api.openai.com/v1/chat/completions', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer YOUR_OPENAI_API_KEY' // Înlocuiește cu cheia ta!
        },
        body: JSON.stringify({
          model: 'gpt-3.5-turbo',
          messages: [{ role: 'user', content: 'Hello from Service Worker!' }]
        })
      })
    );
  }
});
