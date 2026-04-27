import { Injectable } from '@angular/core';
import { EndpointDefinition, ResponseCardRow, ResponseDisplayCard } from '../models/app.models';

@Injectable({ providedIn: 'root' })
export class ResponseCardService {

  buildResponseCards(endpoint: EndpointDefinition | null, response: any): ResponseDisplayCard[] {

    //this is top summary card
    const summary: ResponseDisplayCard = 
    {
      kind: 'summary',
      title: endpoint?.title || 'Response',
      //this is the message from our api response
      subtitle: response?.message || 'Done',
      badge: endpoint?.method || 'API',
      rows: 
      [
        //if response and it's values are present it is included here
        { label: 'Status', value: String(response?.status ?? '-') },
        { label: 'Timestamp', value: String(response?.timestamp??'-') } 
      ]
    };

    //below is dataCards
    const dataCards= this.buildDataCards(response?.data);

    return [summary, ...dataCards];
  }

  private buildDataCards(data: any): ResponseDisplayCard[] {
    //we first check if no data is returned empty means then no value is shown
    if (!data) {
      return [{
        kind: 'data',
        title: 'Returned Data',
        rows: [{ label: 'Value', value: 'No data' }]
      }];
    }
    //if data is an array of objects then it is built
    if (Array.isArray(data)) {
      return data.map(item => this.buildCard(item));
    }
    //data is single object then this is used..why enclosed in [] is in UI we will use map to loop through data
    return [this.buildCard(data)];
  }

  private buildCard(obj: any): ResponseDisplayCard {

  const rows: ResponseCardRow[] = [];
  let highlight: { label: string; value: string } | undefined;

  for (let key in obj) {
    const value = obj[key];

    // highlight only id
    if (!highlight && key.toLowerCase().includes('id')) {
      highlight = {
        label: this.formatLabel(key),
        value: String(value)
      };
      continue;
    }

    if (Array.isArray(value)) {
      rows.push({
        label: this.formatLabel(key),
        bullets: this.toBulletList(value)
      });
    } else {
      rows.push({
        label: this.formatLabel(key),
        value: String(value)
      });
    }
  }

  return {
    kind: 'data',
    title: 'Returned Data',
    highlight,
    rows
  };
}
  //  label formatting
  private formatLabel(key: string): string {
    return key
    ///g means match this pattern for all occurences not just 1st one
    //$1 and $2 means group 1 of chars and grp 2
      .replace(/([a-z])([A-Z])/g, '$1 $2')
      .replace(/\./g, ' ')
      .replace(/[_-]/g, ' ');
  }

  //  bullet list (handles objects also)
  private toBulletList(arr: any[]): string[] {
    return arr.map(item => {

      if (typeof item === 'object' && item !== null) 
        {
        return Object.entries(item)
          .map(([k, v]) => `${this.formatLabel(k)}: ${v}`)
          .join(', ');
      }

      return String(item);
    });
  }
}