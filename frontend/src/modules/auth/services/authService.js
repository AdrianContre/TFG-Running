export const loginService = async (username, password) => {
    return fetch("http://localhost:8081/api/v1/auth/login", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ username: username, password: password })
    })
    .then(response => {
      if (!response.ok) {
        throw new Error(response);
      }
      return response.json();
    })
    .then(data => {
      console.log(data);
      return data;
    })
    .catch(error => {
      console.error('Error:', error);
      throw error; // Lanzar el error para que pueda ser manejado por quien llame a esta función
    });
  };


  