import React from "react";
import { useNavigate } from "react-router";
import "../styles/ResultsList.css"

const ResultsList = ({ results }) => {
    const navigate = useNavigate()
    const handleViewResult = (result) => {
        console.log(result)
        if (result.type === "RunningResult") {
            
            navigate('/viewrunningresult', { state: { sessionId: result.resultId } })
        }
        else if (result.type === "StrengthResult") {
            navigate('/viewstrengthresult', { state: { sessionId: result.resultId } })
        }
        else {
            navigate('/viewmobilityresult', { state: { sessionId: result.resultId } })
        }
    }
    return (
        <>
            <h1 style={{marginTop: "10px", marginLeft: '20px', marginBottom: '20px'}}>Resultados de las sesiones de los usuarios:</h1>
            <div className="results-container">
            {results.map((result) => (
                <div className="result-card" key={result.resultId} onClick={() => {handleViewResult(result)}}>
                    <p className="result-text">
                    El usuario <strong>{result.userFullName}</strong> (@{result.username}) ha completado la sesión{" "}
                    <strong>{result.sessionName}</strong> el{" "}
                    <strong>
                    {new Date(result.date).toLocaleDateString("es-ES", {
                        weekday: "short",
                        day: "2-digit",
                        month: "2-digit",
                        year: "numeric",
                    })}
                    </strong>.
                </p>
                </div>
            ))}
            </div>
        </>
    );
};

export default ResultsList;
