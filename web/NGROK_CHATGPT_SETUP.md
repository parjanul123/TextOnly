# Ngrok setup for Spring Boot backend

## 1. Download and install ngrok
- Go to https://ngrok.com/download and download ngrok for Windows.
- Unzip the ngrok.exe to a known location (e.g., D:\TextOnly\web\ngrok.exe).

## 2. (Optional) Authenticate ngrok
- Create a free account on ngrok.com.
- Run in terminal: `ngrok config add-authtoken <your_token>`

## 3. Start your Spring Boot backend
- In terminal:
  ```
  mvn spring-boot:run
  ```

## 4. Expose your local backend (default port 8080)
- In terminal:
  ```
  ngrok http 8080
  ```
- Vei primi un URL public (ex: https://xxxx.ngrok.io) care redirecționează către localhost:8080.

## 5. Folosește URL-ul ngrok pentru testare sau integrare externă.

---

# ChatGPT Service Worker Example

1. Creează un fișier nou în `static/` numit `chatgpt-sw.js` cu următorul conținut:

```js
// chatgpt-sw.js
self.addEventListener('fetch', event => {
  const url = new URL(event.request.url);
  if (url.pathname === '/chatgpt-proxy') {
    event.respondWith(
      fetch('https://api.openai.com/v1/chat/completions', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer YOUR_OPENAI_API_KEY'
        },
        body: JSON.stringify({
          model: 'gpt-3.5-turbo',
          messages: [{ role: 'user', content: 'Hello from Service Worker!' }]
        })
      })
    );
  }
});
```

2. În pagina ta HTML (ex: `chat.html`), adaugă:

```html
<script>
if ('serviceWorker' in navigator) {
  navigator.serviceWorker.register('/chatgpt-sw.js');
}
</script>
```

3. Pentru a folosi proxy-ul, fă un fetch către `/chatgpt-proxy` din frontend:

```js
fetch('/chatgpt-proxy', { method: 'POST' })
  .then(r => r.json())
  .then(console.log);
```

**Notă:** Înlocuiește `YOUR_OPENAI_API_KEY` cu cheia ta reală. Pentru producție, nu expune cheia în frontend, folosește un backend proxy!
