import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSmile, faFrown, faMeh } from '@fortawesome/free-regular-svg-icons';
import '../styles/rating.css'
import { useEffect } from 'react';

const RatingComponent = ({ onChange }) => {
    const [selectedRating, setSelectedRating] = useState(null);

    // Iconos para los niveles de satisfacción
    const ratings = [
        { value: 1, label: "Fatal", icon: faFrown },
        { value: 2, label: "Mal", icon: faFrown },
        { value: 3, label: "Regular", icon: faMeh },
        { value: 4, label: "Bien", icon: faSmile },
        { value: 5, label: "Estupendamente", icon: faSmile },
    ];

    const handleRatingClick = (value) => {
        setSelectedRating(value);
        onChange(value); // Enviar el valor seleccionado al backend
    };


    return (
        <div className="rating-container">
            {ratings.map((rating) => (
                <div
                    key={rating.value}
                    onClick={() => handleRatingClick(rating.value)}
                    className={`rating-icon ${selectedRating === rating.value ? 'selected' : ''}`}
                >
                    <FontAwesomeIcon icon={rating.icon} />
                    <p>{rating.label}</p>
                </div>
            ))}
        </div>
    );
};

export default RatingComponent;
