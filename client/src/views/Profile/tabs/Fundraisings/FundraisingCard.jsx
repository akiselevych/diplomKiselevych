import styles from "./styles.module.scss";
import {useDispatch} from "react-redux";
import {closeFundraising} from "../../../../store/slices/Volunteer.slice.jsx";
import {instance} from "../../../../api/index.js";

const FundraisingCard = ({f,isPersonalProfile, onDonate,onReport}) => {

    const dispatch = useDispatch();

    const handleDownload = async () => {
        await instance({
            url: `http://localhost:9000/api/fundraising/report/download/${f.fundraisingReport.id}`,
            method: 'GET',
            responseType: 'blob'
        })
            .then(response => {
                const url = window.URL.createObjectURL(new Blob([response.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', `report.${response.data.type.split('/')[1]}`);
                document.body.appendChild(link);
                link.click();
                link.remove();
            })
            .catch(error => {
                console.error('Error downloading file:', error);
            });
    }

    return (
        <div className={styles.card}>
            <p className={styles.name}>Назва: <span>{f.name}</span></p>
            <p className={styles.desc}>Опис: {f.textContent}</p>
            <p className={styles.sum}>Сума: {f.actualAmount ?? 0} / {f.finalAmount}</p>
            {!!f.fundraisingReport && <p className={styles.name}>
                Звіт: {f.fundraisingReport.textContent}
            </p>}
            <div
                onClick={() => {
                    if (isPersonalProfile && !f.closed) {
                        dispatch(closeFundraising({id: f.id}))
                    }
                }}
                className={`${styles.status} ${!f.closed ? styles.closed : styles.open}`}>{f.closed ? 'Закритий' : 'Відкритий'}</div>
            <div className={styles.btns}>
                {!f.closed && <button className={styles.btn} onClick={onDonate}>Задонатити</button>}
                {f.fundraisingReport && <button onClick={() => handleDownload()} className={styles.btn}>Завантажити звіт</button>}
                {isPersonalProfile && !f.fundraisingReport && <button className={styles.btn} onClick={onReport}>Відзвітуватись</button>}
            </div>
        </div>
    );
};

export default FundraisingCard;
