import { Injectable } from '@angular/core';

import { EndpointDefinition, ResponseCardRow, ResponseDisplayCard } from '../models/app.models';

@Injectable({ providedIn: 'root' })
export class ResponseCardService {
  buildResponseCards(endpoint: EndpointDefinition | null, response: unknown): ResponseDisplayCard[] {
    return [this.buildSummaryCard(endpoint, response), ...this.buildDataCards(this.getResponseField(response, 'data'))];
  }

  getStatus(response: unknown): string {
    return String(this.getResponseField(response, 'status') ?? '-');
  }

  getMessage(response: unknown): string {
    return String(this.getResponseField(response, 'message') ?? 'Request completed');
  }

  getTimestamp(response: unknown): string | null {
    const value = this.getResponseField(response, 'timestamp');
    return typeof value === 'string' ? value : null;
  }

  private buildSummaryCard(endpoint: EndpointDefinition | null, response: unknown): ResponseDisplayCard {
    const rows: ResponseCardRow[] = [];
    rows.push({ label: 'Status', value: this.getStatus(response) });

    const timestamp = this.getTimestamp(response);

    if (timestamp) {
      rows.push({ label: 'Timestamp', value: timestamp });
    }

    return {
      kind: 'summary',
      title: endpoint?.title ?? 'Response',
      subtitle: this.getMessage(response),
      badge: endpoint?.method ?? 'API',
      rows,
    };
  }

  private buildDataCards(data: unknown): ResponseDisplayCard[] {
    if (this.isNullish(data)) {
      return [{ kind: 'data', title: 'Returned Data', rows: [{ label: 'Value', value: 'No data returned' }] }];
    }

    if (Array.isArray(data)) {
      if (data.length === 0) {
        return [{ kind: 'data', title: 'Returned Data', rows: [{ label: 'Value', value: 'Empty list' }] }];
      }

      return data.map((item) => this.buildObjectCard(item, 'Returned Data'));
    }

    return [this.buildObjectCard(data, 'Returned Data')];
  }

  private buildObjectCard(data: unknown, title: string): ResponseDisplayCard {
    if (!this.isObject(data)) {
      return { kind: 'data', title, rows: [{ label: 'Value', value: this.formatValue(data) }] };
    }

    const entries = this.toEntries(this.addUsefulTaskFields(data));
    const visibleEntries = entries.filter((entry) => !this.shouldHideValue(entry.value));
    const highlightCandidate = visibleEntries.find((entry) => this.isStrongValue(entry.key, entry.value));
    const rows = entries
      .filter((entry) => entry !== highlightCandidate && !this.shouldHideValue(entry.value))
      .map((entry) => ({
        label: this.formatLabel(entry.key),
        value: this.isBulletList(entry.value) ? undefined : this.formatValue(entry.value),
        bullets: this.isBulletList(entry.value) ? this.toBulletList(entry.value) : undefined,
      }));

    return {
      kind: 'data',
      title,
      highlight: highlightCandidate
        ? { label: this.formatLabel(highlightCandidate.key), value: this.formatValue(highlightCandidate.value) }
        : undefined,
      rows: rows.length ? rows : [{ label: 'Value', value: 'No additional fields' }],
    };
  }

  private getResponseField(response: unknown, field: string): unknown {
    if (!this.isObject(response)) {
      return null;
    }

    return response[field];
  }

  private isObject(value: unknown): value is Record<string, unknown> {
    return typeof value === 'object' && value !== null && !Array.isArray(value);
  }

  private isNullish(value: unknown): value is null | undefined {
    return value === null || value === undefined;
  }

  private toEntries(value: Record<string, unknown>): Array<{ key: string; value: unknown }> {
    return Object.entries(value).map(([key, entryValue]) => ({ key, value: entryValue }));
  }

  private addUsefulTaskFields(data: Record<string, unknown>): Record<string, unknown> {
    const user = data['user'];
    const project = data['project'];

    const enrichedData = {
      ...data,
      userId: data['userId'] ?? (this.isObject(user) ? user['userId'] ?? user['id'] : undefined),
      userName: data['userName'] ?? (this.isObject(user) ? user['fullName'] ?? user['name'] ?? user['username'] : undefined),
      projectId: data['projectId'] ?? (this.isObject(project) ? project['projectId'] ?? project['id'] : undefined),
      projectName: data['projectName'] ?? (this.isObject(project) ? project['projectName'] ?? project['name'] : undefined),
      categories:
        data['categories'] ??
        (Array.isArray(data['categoryResponses']) ? data['categoryResponses'] : this.isObject(data['category']) ? [data['category']] : undefined),
      roles: data['roles'] ?? (Array.isArray(data['authorities']) ? data['authorities'] : undefined),
    };

    return enrichedData;
  }

  private formatLabel(key: string): string {
    return key.replace(/([a-z])([A-Z])/g, '$1 $2').replace(/[_-]/g, ' ');
  }

  private formatValue(value: unknown): string {
    if (this.isNullish(value)) {
      return '-';
    }

    if (['string', 'number', 'boolean'].includes(typeof value)) {
      return String(value);
    }

    if (Array.isArray(value)) {
      return value.map((item) => this.formatValue(item)).join(', ');
    }

    return JSON.stringify(value);
  }

  private isStrongValue(key: string, value: unknown): boolean {
    return ['string', 'number', 'boolean'].includes(typeof value) && /(id|status|total|count|amount|price|value)$/i.test(key);
  }

  private shouldHideValue(value: unknown): boolean {
    return value === null || value === undefined || (Array.isArray(value) && value.length === 0);
  }

  private isBulletList(value: unknown): value is unknown[] {
    return Array.isArray(value);
  }

  private toBulletList(value: unknown[]): string[] {
    return value.map((item) => {
      if (this.isObject(item)) {
        return this.toEntries(item)
          .map((entry) => `${this.formatLabel(entry.key)}: ${this.formatValue(entry.value)}`)
          .join(', ');
      }

      return this.formatValue(item);
    });
  }
}
