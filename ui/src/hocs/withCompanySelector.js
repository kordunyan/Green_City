import { useState } from 'react';
import CompanySelector from '../components/CompanySelector';

export const withCompanySelector = Component => (props) => {
    let [companyId, setCompanyId] = useState(null);

    const onChange = values => {
        props.onChange({
           ...values,
           osbbId: companyId,
        });
    };

    return (
      <>
          {companyId ? <Component {...props} onChange={onChange} /> : <CompanySelector onSelect={setCompanyId} />}
      </>
    );
};
