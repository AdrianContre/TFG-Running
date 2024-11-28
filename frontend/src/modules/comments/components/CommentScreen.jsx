import NavigationBar from "../../home/components/NavigationBar";
import { useState, useRef, useEffect } from "react";
import { Button, Spinner } from "react-bootstrap";
import '../styles/commentScreen.css';
import { useLocation } from "react-router";

function CommentScreen() {
    const location  = useLocation()
    const {planName, num_week, trainingWeekId} = location.state
    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState('');
    const messageContainerRef = useRef(null);
    const userId = JSON.parse(localStorage.getItem('userAuth')).id;
    const [userAuth, setUserAuth] = useState(null)

    useEffect(() => {
        const fetchInfo = async () => {
            //aqui me queda cargar los mensajes con el trainingWeekId
            if (messageContainerRef.current) {
                messageContainerRef.current.scrollTop = messageContainerRef.current.scrollHeight;
            }
            setUserAuth(JSON.parse(localStorage.getItem('userAuth')))
        }
        fetchInfo()
    }, [messages]); 

    const handleSendComment = async () => {
        const messageToSend = {
            content: newMessage,
            author: userId,
            date: new Date().toISOString(),
        };

        try {
            //aqui falta enviar el mensaje al backend
            setMessages((prevMessages) => [...prevMessages, messageToSend]);
            setNewMessage('');
        } catch (error) {
            console.error('Error enviando el mensaje:', error);
            alert('Hubo un problema al enviar el mensaje.');
        }
    };

    const renderComments = () => {
        return messages.map((message, index) => {
            const isUserMessage = message.author.id === userAuth.id;
            const formattedDate = new Date(message.date).toLocaleString('es-ES', {
                hour: '2-digit',
                minute: '2-digit',
                day: 'numeric',
                month: 'short',
            });
            return (
                <div key={index} className={`comment ${isUserMessage ? 'user-comment' : 'other-comment'}`}>
                    <div className="comment-bubble">
                        <div className="comment-author">
                            {isUserMessage ? 'Tú' : `${message.author.name} ${message.author.surname}(@${message.author.username})`}
                        </div>
                        <div className="comment-content">
                            {message.content}
                        </div>
                        <div className="comment-date">
                            {formattedDate}
                        </div>
                    </div>
                </div>
            );
        });
    };

    if (!userAuth) {
        return (
            <div style={{display: 'flex', justifyContent: 'center', marginTop:'25%'}}>
                <Spinner animation="border" role="status"/>
            </div>
        )
    }

    return (
        <>
            <NavigationBar />
            <div className="comment-screen">
                <div className="comment-header">
                    <h2>{planName} - Semana {num_week} - Comentarios</h2>
                </div>
                <div className="comments-container" ref={messageContainerRef}>
                    {renderComments()}
                </div>
                <div className="comment-input-container">
                    <input
                        type="text"
                        placeholder="Escribe un comentario..."
                        className="comment-input"
                        value={newMessage}
                        onChange={(e) => setNewMessage(e.target.value)}
                        onKeyDown={(e) => {
                            if (e.key === 'Enter') {
                                e.preventDefault();
                                handleSendComment();
                            }
                        }}
                    />
                    <button className="send-button" onClick={handleSendComment}>
                        Enviar
                    </button>
                </div>
            </div>
        </>
    );
}

export default CommentScreen;
