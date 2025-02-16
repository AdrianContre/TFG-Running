import store from '../../../redux/stores/store.js'
const COMMENT_ENDPOINT = "http://localhost:8081/api/v1/comments"
const authHeader = `Bearer ${store.getState().auth.token}`

export const getTrainingWeekComments = async (trainingWeekId) => {
    const authHeader = `Bearer ${store.getState().auth.token}`
    return fetch(`${COMMENT_ENDPOINT}/trainingweeks/${trainingWeekId}`,{
        method: 'GET',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        },
    })
    .then(response => {
        return response.json();
      })
      .then(data => {
        console.log(data)
        return data.data
      })
      .catch(error => {
        console.error('Error:', error);
        console.log(error)
        throw error; 
      });
}

export const createComment = async (userId, content, trainingWeekId) => {
    const authHeader = `Bearer ${store.getState().auth.token}`
    return fetch(`${COMMENT_ENDPOINT}/trainingweeks/${trainingWeekId}`,{
        method: 'POST',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        },
        body: JSON.stringify({userId: userId, content: content})
    })
    .then(response => {
        return response.json();
      })
      .then(data => {
        console.log(data)
        return data.data
      })
      .catch(error => {
        console.error('Error:', error);
        console.log(error)
        throw error; 
      });
}