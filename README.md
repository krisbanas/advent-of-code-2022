# Advent of Code

Solutions for 2024.

No judging.


<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>My Jira Worklogs</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 20px; }
    table { border-collapse: collapse; width: 60%; margin-top: 10px; }
    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
    th { background-color: #f2f2f2; }
  </style>
</head>
<body>
  <h1>Last 5 Days - Jira Logged Time</h1>
  <table id="worklogTable">
    <thead>
      <tr>
        <th>Date</th>
        <th>Ticket</th>
        <th>Hours Logged</th>
      </tr>
    </thead>
    <tbody></tbody>
  </table>

  <script>
    const jiraDomain = 'https://your-domain.atlassian.net';
    const email = 'your-email@example.com';
    const apiToken = 'your-api-token';
    const userAccountId = 'your-account-id'; // Needed for filtering

    async function fetchWorklogs() {
      // Calculate date range for last 5 days
      const endDate = new Date();
      const startDate = new Date();
      startDate.setDate(endDate.getDate() - 5);
      
      // Jira API - Get issues with worklogs by current user in last 5 days
      const jql = `worklogAuthor = ${userAccountId} AND worklogDate >= "${startDate.toISOString().split('T')[0]}"`;
      const url = `${jiraDomain}/rest/api/3/search?jql=${encodeURIComponent(jql)}&fields=worklog,key`;

      const response = await fetch(url, {
        headers: {
          'Authorization': 'Basic ' + btoa(`${email}:${apiToken}`),
          'Accept': 'application/json'
        }
      });
      const data = await response.json();

      const rows = [];
      const dateMap = {}; // group by date

      data.issues.forEach(issue => {
        const key = issue.key;
        (issue.fields.worklog.worklogs || []).forEach(log => {
          if (log.author.accountId === userAccountId) {
            const logDate = log.started.split('T')[0];
            if (new Date(logDate) >= startDate) {
              const hours = log.timeSpentSeconds / 3600;
              rows.push({ date: logDate, ticket: key, hours: hours });
            }
          }
        });
      });

      renderTable(rows);
    }

    function renderTable(entries) {
      const tbody = document.querySelector('#worklogTable tbody');
      tbody.innerHTML = '';
      entries.sort((a, b) => new Date(b.date) - new Date(a.date)); // newest first
      entries.forEach(entry => {
        const tr = document.createElement('tr');
        tr.innerHTML = `<td>${entry.date}</td><td>${entry.ticket}</td><td>${entry.hours.toFixed(2)}</td>`;
        tbody.appendChild(tr);
      });
    }

    fetchWorklogs().catch(err => console.error(err));
  </script>
</body>
</html>