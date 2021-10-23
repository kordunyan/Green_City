import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Osbb from './osbb';
import TrashCompany from './trash-company';
import TrashExportation from './trash-exportation';
import FullTrashImages from './full-trash-images';
import EmptyTrashImages from './empty-trash-images';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}osbb`} component={Osbb} />
      <ErrorBoundaryRoute path={`${match.url}trash-company`} component={TrashCompany} />
      <ErrorBoundaryRoute path={`${match.url}trash-exportation`} component={TrashExportation} />
      <ErrorBoundaryRoute path={`${match.url}full-trash-images`} component={FullTrashImages} />
      <ErrorBoundaryRoute path={`${match.url}empty-trash-images`} component={EmptyTrashImages} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
