import NavigationBar from "../../home/components/NavigationBar";
import { useState, useRef, useEffect } from "react";
import { Button, Spinner } from "react-bootstrap";
import '../styles/commentScreen.css';
import { useLocation } from "react-router";
import { createComment, getTrainingWeekComments } from "../services/commentsService";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faCertificate} from '@fortawesome/free-solid-svg-icons';

function CommentScreen() {
    const location  = useLocation()
    const {planName, num_week, trainingWeekId, planCreatorId} = location.state
    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState('');
    const messageContainerRef = useRef(null);
    const [userAuth, setUserAuth] = useState(null)

    useEffect(() => {
        console.log(planCreatorId)
        const fetchInfo = async () => {
            const comments = await getTrainingWeekComments(trainingWeekId)
            setMessages(comments)
            if (messageContainerRef.current) {
                messageContainerRef.current.scrollTop = messageContainerRef.current.scrollHeight;
            }
            setUserAuth(JSON.parse(localStorage.getItem('userAuth')))
        }
        fetchInfo()
    }, []); 

    const handleSendComment = async () => {
        const messageToSend = {
            content: newMessage,
            author: {
                "id": userAuth.id, 
                "name": userAuth.name,
                "surname": userAuth.surname,
                "username": userAuth.username
            },
            date: new Date().toISOString(),
        };

        try {
            const sendComment = await createComment(userAuth.id, newMessage, trainingWeekId);
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
            const previousMessage = messages[index - 1];
            const isSameAuthorAsPrevious = previousMessage?.author.id === message.author.id;
    
            return (
                <div key={index} className={`comment ${isUserMessage ? 'user-comment' : 'other-comment'}`}>
                    <div className="comment-bubble">
                        {!isSameAuthorAsPrevious && (
                            <div className="comment-author">
                                <strong>
                                    {isUserMessage
                                        ? 'Tú'
                                        : `${message.author.name} ${message.author.surname}(@${message.author.username})`}
                                </strong>
                                <span style={{ marginLeft: '2px' }}>
                                    {planCreatorId === message.author.id ? (
                                        <FontAwesomeIcon icon={faCertificate} />
                                    ) : null}
                                </span>
                            </div>
                        )}
                        <div className="comment-content">{message.content}</div>
                        <div className="comment-date">{formattedDate}</div>
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
                        onChange={(e) => {
                            if (e.target.value.length <= 1000) {
                                setNewMessage(e.target.value);
                            }
                        }}
                        onKeyDown={(e) => {
                            if (e.key === 'Enter') {
                                e.preventDefault();
                                handleSendComment();
                            }
                        }}
                    />
                    <div className={`character-counter ${newMessage.length === 1000 ? 'exceeded' : ''}`}>
                        {newMessage.length}/1000
                    </div>
                    <button className="send-button" onClick={handleSendComment}>
                        Enviar
                    </button>
                </div>
            </div>
        </>
    );
}

export default CommentScreen;
