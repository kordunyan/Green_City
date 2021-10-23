import {useState} from 'react';
import QrReader from 'react-qr-reader';
import { Button, Input } from 'antd';

const CompanySelector = ({ onSelect }) => {
    const [error, setError] = useState(false);
    const [qrCode, setQrCode] = useState('');

    const handleScan = data => onSelect(data);

    const handleError = error => setError(error);

    const handleInputChange = event => setQrCode(event.target.value);

    const handleSubmitQR = () => onSelect(qrCode);

    return (
      <div>
          {!error && (
              <QrReader
                delay={300}
                onScan={handleScan}
                onError={handleError}
                style={{ width: '100%' }}
            />
          )}
          {error && (
              <div>
                  <Input value={qrCode} onChange={handleInputChange} />
                  <Button onClick={handleSubmitQR}></Button>
              </div>
          )}
      </div>
    );
};

export default CompanySelector;